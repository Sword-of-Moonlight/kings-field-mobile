import com.nttdocomo.ui.PhoneSystem;

public final class J_Device 
{
  final int KEY_NUM0 = 1;
  
  final int KEY_NUM1 = 2;
  
  final int KEY_NUM2 = 4;
  
  final int KEY_NUM3 = 8;
  
  final int KEY_NUM4 = 16;
  
  final int KEY_NUM5 = 32;
  
  final int KEY_NUM6 = 64;
  
  final int KEY_NUM7 = 128;
  
  final int KEY_NUM8 = 256;
  
  final int KEY_NUM9 = 512;
  
  final int KEY_UP = 131072;
  
  final int KEY_LEFT = 65536;
  
  final int KEY_RIGHT = 262144;
  
  final int KEY_DOWN = 524288;
  
  final int KEY_SOFT1 = 2097152;
  
  final int KEY_SOFT2 = 4194304;
  
  final int KEY_FIRE = 1048576;
  
  public int m_Trg = 0;
  
  public int m_Cont = 0;
  
  public int m_ContCnt = 0;
  
  public int m_ContOld = 0;
  
  public boolean m_VibFlg = false;
  
  // Store the runtime for later use...
  private Runtime jre;

  /**
   * Constructor. 
  **/
  public J_Device()
  {
    jre = Runtime.getRuntime();
  }

  public void Init() 
  {
    this.m_Trg = 0;
    this.m_Cont = 0;
    this.m_ContCnt = 0;
    this.m_ContOld = 0;
    this.m_VibFlg = false;
  }
  
  public void KeyRead() 
  {
    int i = KingsField_P6Main.canvas.getKeypadState();
    this.m_Trg = i & (i ^ this.m_Cont);
    this.m_ContOld = this.m_Cont;
    this.m_Cont = i;
    if (this.m_ContOld == this.m_Cont) {
      this.m_ContCnt++;
    } else {
      this.m_ContCnt = 0;
    } 
  }
  
    public void SetVibMode(boolean paramBoolean) 
    {
        this.m_VibFlg = paramBoolean;
        if (this.m_VibFlg == true) 
        {
          PhoneSystem.setAttribute(1, 1);
        } 
        else 
        {
          PhoneSystem.setAttribute(1, 0);
        } 
    }

    public long GetTotalMemory()
    {
        return jre.totalMemory();
    }

    public long GetFreeMemory()
    {
        return jre.freeMemory();
    }

    public void MemoryGC()
    {
        jre.gc();
    }
}