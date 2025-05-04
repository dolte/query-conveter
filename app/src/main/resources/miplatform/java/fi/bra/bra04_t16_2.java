
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_t16_2
{
    public bra04_t16_2()
    {
    }
    //##**$$adj_mng
    /*
    */
    public DOBJ CTLadj_mng(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().getDouble("SEQ") > 0)
            {
                dobj  = CALLadj_mng_UPD6(Conn, dobj);           //  �����ݾ� ���
            }
            else
            {
                dobj  = CALLadj_mng_SEL9(Conn, dobj);           //  SEQ ȹ��
                dobj  = CALLadj_mng_INS8(Conn, dobj);           //  �����ݾ� ���
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
    public DOBJ CTLadj_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().getDouble("SEQ") > 0)
        {
            dobj  = CALLadj_mng_UPD6(Conn, dobj);           //  �����ݾ� ���
        }
        else
        {
            dobj  = CALLadj_mng_SEL9(Conn, dobj);           //  SEQ ȹ��
            dobj  = CALLadj_mng_INS8(Conn, dobj);           //  �����ݾ� ���
        }
        return dobj;
    }
    // �����ݾ� ���
    // �ش����� �̼������� ������
    public DOBJ CALLadj_mng_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLadj_mng_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLadj_mng_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double   COMIS_AMT = dvobj.getRecord().getDouble("COMIS_AMT");   //������ �ݾ�
        String   BEFORE_ADJ_AMT = dvobj.getRecord().get("BEFORE_ADJ_AMT");
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   ADJ_GBN = dvobj.getRecord().get("ADJ_GBN");   //���� ����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   NONPY_DAY = dvobj.getRecord().get("NONPY_DAY");   //�̳�����
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //���� �ݾ�
        String   BIGO = dvobj.getRecord().get("BIGO");   //������
        String   BEFORE_COMIS_AMT = dvobj.getRecord().get("BEFORE_COMIS_AMT");
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_MISU_ADJ  \n";
        query +=" set  \n";
        query +=" BEFORE_COMIS_AMT=:BEFORE_COMIS_AMT , INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID , BIGO=:BIGO , ADJ_AMT=:ADJ_AMT , ADJ_GBN=:ADJ_GBN ,  \n";
        query +=" BEFORE_ADJ_AMT=:BEFORE_ADJ_AMT , COMIS_AMT=:COMIS_AMT  \n";
        query +=" where NONPY_DAY=:NONPY_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("COMIS_AMT", COMIS_AMT);               //������ �ݾ�
        sobj.setString("BEFORE_ADJ_AMT", BEFORE_ADJ_AMT);
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("ADJ_GBN", ADJ_GBN);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("NONPY_DAY", NONPY_DAY);               //�̳�����
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //���� �ݾ�
        sobj.setString("BIGO", BIGO);               //������
        sobj.setString("BEFORE_COMIS_AMT", BEFORE_COMIS_AMT);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // SEQ ȹ��
    public DOBJ CALLadj_mng_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLadj_mng_SEL9(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        rvobj.Println("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLadj_mng_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NONPY_DAY = dobj.getRetObject("S").getRecord().get("NONPY_DAY");   //�̳�����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_MISU_ADJ  WHERE  NONPY_DAY  =  :NONPY_DAY   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("NONPY_DAY", NONPY_DAY);               //�̳�����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ݾ� ���
    public DOBJ CALLadj_mng_INS8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("INS8");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLadj_mng_INS8(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS8") ;
        rvobj.Println("INS8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLadj_mng_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double   COMIS_AMT = dvobj.getRecord().getDouble("COMIS_AMT");   //������ �ݾ�
        String   BEFORE_ADJ_AMT = dvobj.getRecord().get("BEFORE_ADJ_AMT");
        String   ADJ_GBN = dvobj.getRecord().get("ADJ_GBN");   //���� ����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   NONPY_DAY = dvobj.getRecord().get("NONPY_DAY");   //�̳�����
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //���� �ݾ�
        String   BIGO = dvobj.getRecord().get("BIGO");   //������
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //����� ID
        String   BEFORE_COMIS_AMT = dvobj.getRecord().get("BEFORE_COMIS_AMT");
        double   SEQ = dobj.getRetObject("SEL9").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_MISU_ADJ (BEFORE_COMIS_AMT, INS_DATE, INSPRES_ID, BIGO, ADJ_AMT, NONPY_DAY, UPSO_CD, ADJ_GBN, SEQ, BEFORE_ADJ_AMT, COMIS_AMT)  \n";
        query +=" values(:BEFORE_COMIS_AMT , SYSDATE, :INSPRES_ID , :BIGO , :ADJ_AMT , :NONPY_DAY , :UPSO_CD , :ADJ_GBN , :SEQ , :BEFORE_ADJ_AMT , :COMIS_AMT )";
        sobj.setSql(query);
        sobj.setDouble("COMIS_AMT", COMIS_AMT);               //������ �ݾ�
        sobj.setString("BEFORE_ADJ_AMT", BEFORE_ADJ_AMT);
        sobj.setString("ADJ_GBN", ADJ_GBN);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("NONPY_DAY", NONPY_DAY);               //�̳�����
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //���� �ݾ�
        sobj.setString("BIGO", BIGO);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("BEFORE_COMIS_AMT", BEFORE_COMIS_AMT);
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    //##**$$adj_mng
    //##**$$end
}
