import com.nttdocomo.ui.AudioPresenter;
import com.nttdocomo.ui.MediaManager;
import com.nttdocomo.ui.MediaSound;

class J_Sound {
  private MediaSound[] m_aAudioData = null;
  
  private AudioPresenter m_AudioTrack = null;
  
  private int m_nAudioNum = 0;
  
  private boolean m_enable = true;
  
  public void playMMF(int paramInt) {
    if (!this.m_enable)
      return; 
    if (paramInt >= this.m_nAudioNum)
      return; 
    try {
      stop();
      this.m_AudioTrack.setSound(this.m_aAudioData[paramInt]);
      this.m_AudioTrack.play();
    } catch (Exception exception) {}
  }
  
  public void enable(boolean paramBoolean) {
    this.m_enable = paramBoolean;
  }
  
  public void stop() {
    if (this.m_AudioTrack != null && this.m_AudioTrack.getMediaResource() != null) {
      byte b = 0;
      while (b < 10) {
        try {
          this.m_AudioTrack.stop();
          b = 10;
        } catch (Exception exception) {
          b++;
          try {
            Thread.sleep(10L);
          } catch (Exception exception1) {}
        } 
      } 
    } 
  }
  
  public void init(KingsField_P6Canvas paramKingsField_P6Canvas) {
    try {
      this.m_nAudioNum = 5;
      this.m_aAudioData = new MediaSound[this.m_nAudioNum];
      this.m_AudioTrack = AudioPresenter.getAudioPresenter(0);
      this.m_AudioTrack.setMediaListener(paramKingsField_P6Canvas);
      for (byte b = 0; b < this.m_nAudioNum; b++) {
        byte[] arrayOfByte = KingsField_P6Canvas.loadResourceData(49 + b);
        this.m_aAudioData[b] = MediaManager.getSound(arrayOfByte);
        this.m_aAudioData[b].use();
      } 
    } catch (Exception exception) {}
  }
}