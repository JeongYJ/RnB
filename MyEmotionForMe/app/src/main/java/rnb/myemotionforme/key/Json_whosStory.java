package rnb.myemotionforme.key;

/**
 * Created by yj on 16. 12. 7..
 */
public class Json_whosStory {


    public String sdate;
    public String stitle;
    public String stext;
    public int sshow;
    public int sdno;
    public int sno;

    public int sposition;


    public Json_whosStory()
    {}

    public Json_whosStory(String date, String title, String text, int show, int dno, int sno_)
    {
        sdate = date;
        stitle = title;
        stext = text;
        sshow = show;
        sdno = dno;
        sno = sno_;
    }

    public Json_whosStory(int position)
    {
        sposition = position;
    }

    public String getDate()
    {
        return sdate;
    }

    public String getTitle()
    {
        return stitle;
    }

    public String getText()
    {
        return stext;
    }

    public int getShow()
    {
        return sshow;
    }

    public int getDno()
    {
        return sdno;
    }

    public int getPosition()
    {
        return sposition;
    }

    public int getSno(){ return sno; }

    //sno, sdate, stitle, stext, sshow, rdno 값 받기

}
