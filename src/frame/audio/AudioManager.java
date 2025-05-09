package frame.audio;

import javax.sound.midi.*;
import javax.sound.sampled.*;

/**
 * 专业级游戏音频管理器（修复越界+提升音质）
 */
public class AudioManager {
    private static AudioManager instance;
    private Sequencer sequencer;
    private Synthesizer synthesizer; // 新增：使用合成器增强音色
    private boolean bgmEnabled = true;
    private boolean sfxEnabled = true;

    public enum SoundEffectType {
        MOVE, VICTORY, ERROR, TIMER_WARNING
    }

    private AudioManager() {
        initMidi();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    private void initMidi() {
        try {
            // 初始化MIDI合成器（替代直接使用Sequencer）
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    // 专业级背景音乐生成（多轨道+和声+动态）
    public void generateBackgroundMusic() {
        if (!bgmEnabled || sequencer == null) return;
        try {
            Sequence sequence = new Sequence(Sequence.PPQ, 24);
            Track mainTrack = sequence.createTrack();
            Track bassTrack = sequence.createTrack();
            Track padTrack = sequence.createTrack();

            setInstrument(mainTrack, 0, 0);
            setInstrument(bassTrack, 1, 33);
            setInstrument(padTrack, 2, 50);

            setTempo(sequence, 100);

            // 确保数组长度一致（示例修正）
            int[] melody = {60, 64, 67, 72, 67, 64, 60, 65, 69, 74, 69, 65, 60, 64, 67, 72}; // 16个元素
            int[] melodyRhythm = {2, 2, 2, 2, 2, 2, 4, 2, 2, 2, 2, 4, 2, 2, 2, 2}; // 16个元素
            int[] bass = {48, 48, 50, 50, 52, 52, 48, 48, 53, 53, 55, 55, 48, 48, 50, 50}; // 16个元素
            int[] bassRhythm = {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4}; // 16个元素
            int[] pad = {64, 68, 71, 76, 71, 68, 64, 69, 73, 78, 73, 69, 64, 68, 71, 76}; // 16个元素
            int[] padRhythm = {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}; // 16个元素

            long tick = 0;
            for (int i = 0; i < melody.length; i++) { // 确保 i < 数组长度
                addNote(mainTrack, 0, melody[i], 85, tick, melodyRhythm[i] * 24);
                tick += melodyRhythm[i] * 24;
            }

            tick = 0;
            for (int i = 0; i < bass.length; i++) {
                addNote(bassTrack, 1, bass[i], 75, tick, bassRhythm[i] * 24);
                tick += bassRhythm[i] * 24;
            }

            tick = 0;
            for (int i = 0; i < pad.length; i++) {
                addNote(padTrack, 2, pad[i], 65, tick, padRhythm[i] * 24);
                tick += padRhythm[i] * 24;
            }

            sequencer.setSequence(sequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }


    // 播放音效（彻底修复越界+提升音质）
    public void playSoundEffect(SoundEffectType type) {
        if (!sfxEnabled) return;

        new Thread(() -> {
            try {
                int sampleRate = 44100;
                AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
                SourceDataLine line = AudioSystem.getSourceDataLine(format);
                line.open(format);
                line.start();

                // 确保缓冲区长度为偶数（16位音频必须）
                int bufferSize = (sampleRate / 4) / 2 * 2; // 强制偶数
                byte[] buffer = new byte[bufferSize];

                switch (type) {
                    case MOVE:
                        // 优化：使用短时长+高频衰减，模拟清脆的点击感
                        generateTone(buffer, 880.0, sampleRate, 0.1f, 0.3f, 0.5f, 0.2f);
                        line.write(buffer, 0, buffer.length);
                        break;
                    case VICTORY:
                        // 优化：使用大三和弦+渐强包络，传递喜悦感
                        generateChord(buffer, new double[]{523.25, 659.25, 783.99}, sampleRate);
                        line.write(buffer, 0, buffer.length);
                        break;
                    case ERROR:
                        // 优化：使用降三和弦+低频震颤，传递挫败感
                        generateChord(buffer, new double[]{349.23, 392.00, 440.00}, sampleRate);
                        line.write(buffer, 0, buffer.length);
                        break;
                    case TIMER_WARNING:
                        // 优化：高频+快速重复，增强紧迫感
                        generateTone(buffer, 1046.50, sampleRate, 0.05f, 0.2f, 0.4f, 0.3f);
                        line.write(buffer, 0, buffer.length);
                        line.write(buffer, 0, buffer.length); // 重复播放
                        break;
                }

                line.drain();
                line.close();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // 辅助方法：设置乐器
    private void setInstrument(Track track, int channel, int program) throws InvalidMidiDataException {
        ShortMessage msg = new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, program, 0);
        track.add(new MidiEvent(msg, 0));
    }

    // 辅助方法：设置BPM
    private void setTempo(Sequence sequence, int bpm) throws InvalidMidiDataException {
        Track track = sequence.getTracks()[0];
        MetaMessage msg = new MetaMessage();
        byte[] data = {(byte) (0x07 & 0xFF), (byte) (0xA1 & 0xFF), (byte) (0x20 & 0xFF)}; // 100BPM对应值
        msg.setMessage(0x51, data, 3);
        track.add(new MidiEvent(msg, 0));
    }

    // 辅助方法：添加音符（带力度动态）
    private void addNote(Track track, int channel, int note, int velocity, long startTick, long duration) throws InvalidMidiDataException {
        // 音符开始（动态力度：随时间逐渐增强）
        ShortMessage onMsg = new ShortMessage(ShortMessage.NOTE_ON, channel, note, velocity + (int)(startTick * 0.01));
        track.add(new MidiEvent(onMsg, startTick));

        // 音符结束（带释放力度）
        ShortMessage offMsg = new ShortMessage(ShortMessage.NOTE_OFF, channel, note, velocity - 20);
        track.add(new MidiEvent(offMsg, startTick + duration));
    }

    // 增强版音调生成（支持自定义包络参数）
    private void generateTone(byte[] buffer, double frequency, int sampleRate,
                              float attackRatio, float decayRatio, float sustainLevel, float releaseRatio) {
        int attack = (int) (buffer.length * attackRatio);
        int decay = (int) (buffer.length * decayRatio);
        int release = (int) (buffer.length * releaseRatio);
        int sustain = buffer.length - attack - decay - release;

        for (int i = 0; i < buffer.length; i += 2) {
            if (i + 1 >= buffer.length) break; // 防御性越界检查

            float envelope = 1.0f;
            if (i < attack) {
                envelope = (float) i / attack; // 指数上升
            } else if (i < attack + decay) {
                envelope = 1.0f - (1.0f - sustainLevel) * (float) (i - attack) / decay; // 对数衰减
            } else if (i < attack + decay + sustain) {
                envelope = sustainLevel; // 持续阶段
            } else {
                envelope = sustainLevel * (float) (buffer.length - i) / release; // 线性释放
            }

            double angle = i / (sampleRate / frequency) * 2.0 * Math.PI;
            short amplitude = (short) (Short.MAX_VALUE * 0.6 * envelope * Math.sin(angle)); // 0.6防止削波

            buffer[i] = (byte) (amplitude & 0xFF);
            buffer[i + 1] = (byte) ((amplitude >> 8) & 0xFF);
        }
    }

    // 新增：和弦生成方法（多音调叠加）
    private void generateChord(byte[] buffer, double[] frequencies, int sampleRate) {
        for (int i = 0; i < buffer.length; i += 2) {
            if (i + 1 >= buffer.length) break;

            float sum = 0;
            for (double freq : frequencies) {
                double angle = i / (sampleRate / freq) * 2.0 * Math.PI;
                sum += Math.sin(angle);
            }
            sum /= frequencies.length; // 归一化

            short amplitude = (short) (Short.MAX_VALUE * 0.5 * sum); // 和弦音量削弱
            buffer[i] = (byte) (amplitude & 0xFF);
            buffer[i + 1] = (byte) ((amplitude >> 8) & 0xFF);
        }
    }

    // 基础音调生成（兼容旧调用）
    private void generateTone(byte[] buffer, double frequency, int sampleRate) {
        generateTone(buffer, frequency, sampleRate, 0.1f, 0.3f, 0.6f, 0.2f);
    }

    // 以下为原有控制方法（无修改）
    public boolean isBgmEnabled() { return bgmEnabled; }
    public boolean isSfxEnabled() { return sfxEnabled; }
    public void setBgmEnabled(boolean enabled) {
        this.bgmEnabled = enabled;
        if (sequencer != null) {
            if (enabled) sequencer.start();
            else sequencer.stop();
        }
    }
    public void setSfxEnabled(boolean enabled) { this.sfxEnabled = enabled; }
}