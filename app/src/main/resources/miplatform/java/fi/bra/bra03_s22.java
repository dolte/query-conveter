
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s22
{
    public bra03_s22()
    {
    }
    //##**$$send_mail
    /*
    */
    public DOBJ CTLsend_mail(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLsend_mail_MPD1(Conn, dobj);           //  �б�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLsend_mail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsend_mail_MPD1(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLsend_mail_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("EMAIL_ADDR").equals(""))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsend_mail_XIUD4(Conn, dobj);           //  ���� ť ����
            }
        }
        return dobj;
    }
    // ���� ť ����
    public DOBJ CALLsend_mail_XIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD4");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsend_mail_XIUD4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsend_mail_XIUD4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        String   FROM_EMAIL = dobj.getRetObject("R").getRecord().get("SENDER_MAIL");   //�߽��� �̸����ּ�
        String   FROM_NAME = dobj.getRetObject("R").getRecord().get("SENDER_NM");   //�߽��� �̸�
        String   MAP1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� MAP1
        String   MAP2 = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //���� MAP2
        String   MAP3 = dobj.getRetObject("R").getRecord().get("SUB_NAME");   //���� MAP3
        String   MAP_CONTENT = dobj.getRetObject("R").getRecord().get("MAP_CONTENT");   //������
        String   SUBJECT = dobj.getRetObject("R").getRecord().get("SUBJECT");   //����
        String   TO_EMAIL = dobj.getRetObject("R").getRecord().get("EMAIL_ADDR");   //������ �̸���
        String   TO_NAME = dobj.getRetObject("R").getRecord().get("UPSO_NM");   //������ �̸�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO AMAIL.EMS_MAILQUEUE (SEQ, MAIL_CODE, TO_EMAIL, TO_NAME, FROM_EMAIL, FROM_NAME, SUBJECT, TARGET_FLAG, REG_DATE, MAP1, MAP2, MAP3, MAP_CONTENT) SELECT AMAIL.EMS_MAILQUEUE_SEQ.NEXTVAL , '41' , :TO_EMAIL , :TO_NAME , :FROM_EMAIL , :FROM_NAME , :SUBJECT , 'N' , SYSDATE , :MAP1 , :MAP2 , :MAP3 , :MAP_CONTENT FROM DUAL";
        sobj.setSql(query);
        sobj.setString("FROM_EMAIL", FROM_EMAIL);               //�߽��� �̸����ּ�
        sobj.setString("FROM_NAME", FROM_NAME);               //�߽��� �̸�
        sobj.setString("MAP1", MAP1);               //���� MAP1
        sobj.setString("MAP2", MAP2);               //���� MAP2
        sobj.setString("MAP3", MAP3);               //���� MAP3
        sobj.setString("MAP_CONTENT", MAP_CONTENT);               //������
        sobj.setString("SUBJECT", SUBJECT);               //����
        sobj.setString("TO_EMAIL", TO_EMAIL);               //������ �̸���
        sobj.setString("TO_NAME", TO_NAME);               //������ �̸�
        return sobj;
    }
    //##**$$send_mail
    //##**$$end
}
