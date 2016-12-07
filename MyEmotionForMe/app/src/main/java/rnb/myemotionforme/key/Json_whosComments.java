package rnb.myemotionforme.key;

/**
 * Created by yj on 16. 12. 7..
 */
public class Json_whosComments {

    public String ntext;
    public int nwho;
    public int nsno;
    public int nno;

    public int sposition;

    public Json_whosComments()
    {}

    public Json_whosComments(String text, int who, int sno, int nno_)
    {
        ntext = text;
        nwho = who;
        nsno = sno;
        nno = nno_;
    }

    public Json_whosComments(int position)
    {
        sposition = position;
    }

    public String getText()
    {
        return ntext;
    }

    public int getWho()
    {
        return nwho;
    }

    public int getSno()
    {
        return nsno;
    }

    public int getPosition()
    {
        return sposition;
    }

    public int getNno(){ return nno; }
}
