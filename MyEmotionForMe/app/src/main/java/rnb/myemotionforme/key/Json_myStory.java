package rnb.myemotionforme.key;

/**
 * Created by yj on 16. 11. 9..
 */
public class Json_myStory {

    public String sdate;
    public String stitle;
    public String stext;
    public int sshow;
    public int sdno;
    public int sno;

    public int sposition;


    public Json_myStory()
    {}

    public Json_myStory(String date, String title, String text, int show, int dno, int sno_)
    {
        sdate = date;
        stitle = title;
        stext = text;
        sshow = show;
        sdno = dno;
        sno = sno;
    }

    public Json_myStory(int position)
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
