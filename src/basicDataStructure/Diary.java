package basicDataStructure;
public class Diary {
	
	private String DateTime;
	private int Id;
	private String Title;
	private String Type;
	private String Writer;
	private String StringContent;
        private int VisibleType;
        private byte [] RichContent;
        private byte [] Voice;
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}
	public String getDateTime() {
		return DateTime;
	}
        public void setVisibletType(int f)
        {
            VisibleType=f;
        }
        public int getVisibleType()
        {
            return VisibleType;
        }
	public void setId(int id) {
		Id = id;
	}
	public int getId() {
		return Id;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getTitle() {
		return Title;
	}
	public void setWriter(String user) {
		Writer = user;
	}
	public String getWriter() {
		return Writer;
	}
	public void setStringContent(String content) {
		StringContent = content;
	}
	public String getStringContent() {
		return StringContent;
	}
	public void setDiaryType(String cls) {
		Type=cls;
	}
	public String getDiaryType() {
		return Type;
	}
	public void setRichContent(byte [] rich)
        {
            RichContent=rich;
        }
        public byte [] getRichContent()
        {
            return RichContent;
        }
        public void setVoice(byte [] voice)
        {
            Voice=voice;
        }
        public byte [] getVoice()
        {
            return Voice;
        }
        public DiaryDescription getDescription()
        {
            DiaryDescription pde=new DiaryDescription();
            pde.setId(getId());
            pde.setWriter(getWriter());
            pde.setDateTime(getDateTime());
            pde.setDiaryType(getDiaryType());
            pde.setStringContent(getStringContent());
            pde.setTitle(getTitle());
            return pde;
        }

}
