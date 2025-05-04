
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_t20
{
    public bra04_t20()
    {
    }
    //##**$$get_stat
    /*
    */
    public DOBJ CTLget_stat(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLget_stat_SEL1(Conn, dobj);           //  ������, �������� ȹ��
            dobj  = CALLget_stat_SEL2(Conn, dobj);           //  �������� ������
            if( dobj.getRetObject("SEL2").getRecordCnt() > 0)
            {
                dobj  = CALLget_stat_SEL6(Conn, dobj);           //  ������ ����
                dobj  = CALLget_stat_MRG4( dobj);        //  ������ ������ Ȯ��
                dobj  = CALLget_stat_SEL8(Conn, dobj);           //  �����ȸ
            }
            else
            {
                dobj  = CALLget_stat_SEL7(Conn, dobj);           //  ���� ������
                dobj  = CALLget_stat_MRG4( dobj);        //  ������ ������ Ȯ��
                dobj  = CALLget_stat_SEL8(Conn, dobj);           //  �����ȸ
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
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
    public DOBJ CTLget_stat( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_stat_SEL1(Conn, dobj);           //  ������, �������� ȹ��
        dobj  = CALLget_stat_SEL2(Conn, dobj);           //  �������� ������
        if( dobj.getRetObject("SEL2").getRecordCnt() > 0)
        {
            dobj  = CALLget_stat_SEL6(Conn, dobj);           //  ������ ����
            dobj  = CALLget_stat_MRG4( dobj);        //  ������ ������ Ȯ��
            dobj  = CALLget_stat_SEL8(Conn, dobj);           //  �����ȸ
        }
        else
        {
            dobj  = CALLget_stat_SEL7(Conn, dobj);           //  ���� ������
            dobj  = CALLget_stat_MRG4( dobj);        //  ������ ������ Ȯ��
            dobj  = CALLget_stat_SEL8(Conn, dobj);           //  �����ȸ
        }
        return dobj;
    }
    // ������, �������� ȹ��
    public DOBJ CALLget_stat_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLget_stat_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_stat_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(F_DAY)  AS  FIRST_DAY,  MIN(L_DAY)  AS  LAST_DAY  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  MIN(NONPY_DAY)  AS  F_DAY  ,  MAX(NONPY_DAY)  AS  L_DAY  FROM  GIBU.TBRA_MISU_CHEKWON  WHERE  NONPY_DAY  BETWEEN  :START_YRMN  ||  '01'   \n";
        query +=" AND  :END_YRMN  ||  '31'  GROUP  BY  BRAN_CD  ) ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // �������� ������
    public DOBJ CALLget_stat_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLget_stat_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_stat_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LAST_DAY = dobj.getRetObject("SEL1").getRecord().get("LAST_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD,  SUM(NONPY_AMT)  FROM  GIBU.TBRA_MISU_CHEKWON  WHERE  NONPY_DAY  =  :LAST_DAY  GROUP  BY  BRAN_CD  HAVING  SUM(NONPY_AMT)  =  0 ";
        sobj.setSql(query);
        sobj.setString("LAST_DAY", LAST_DAY);               //������
        return sobj;
    }
    // ������ ����
    public DOBJ CALLget_stat_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLget_stat_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_stat_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LAST_DAY = dobj.getRetObject("SEL1").getRecord().get("LAST_DAY");   //������
        String   FIRST_DAY = dobj.getRetObject("SEL1").getRecord().get("FIRST_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :FIRST_DAY  AS  FIRST_DAY  ,  TO_CHAR(TO_DATE(:LAST_DAY)-1,  'YYYYMMDD')  AS  LAST_DAY  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("LAST_DAY", LAST_DAY);               //������
        sobj.setString("FIRST_DAY", FIRST_DAY);               //������
        return sobj;
    }
    // ������ ������ Ȯ��
    public DOBJ CALLget_stat_MRG4(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG4");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL6, SEL7","");
        rvobj.setName("MRG4") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // �����ȸ
    public DOBJ CALLget_stat_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLget_stat_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_stat_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LAST_DAY = dobj.getRetObject("MRG4").getRecord().get("LAST_DAY");   //������
        String   FIRST_DAY = dobj.getRetObject("MRG4").getRecord().get("FIRST_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  SUM(BEFORE_NONPY_AMT)  AS  BEFORE_NONPY_AMT  ,  SUM(DEMD_AMT)  AS  DEMD_AMT  ,  SUM(REPT_AMT)  AS  REPT_AMT  ,  SUM(ADJ_AMT)  AS  ADJ_AMT  ,  SUM(NONPY_AMT)  AS  NONPY_AMT  ,  :FIRST_DAY  ||  '  -  '  ||  :LAST_DAY  AS  YRMN  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  SUM(BEFORE_NONPY_AMT)  AS  BEFORE_NONPY_AMT  ,  0  AS  DEMD_AMT  ,  0  AS  REPT_AMT  ,  0  AS  ADJ_AMT  ,  0  AS  NONPY_AMT  FROM  GIBU.TBRA_MISU_CHEKWON  WHERE  NONPY_DAY  =  :FIRST_DAY  GROUP  BY  BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  BRAN_CD  ,  0  AS  BEFORE_NONPY_AMT  ,  SUM(DEMD_AMT)  AS  DEMD_AMT  ,  SUM(REPT_AMT)  AS  REPT_AMT  ,  SUM(ADJ_AMT)  AS  ADJ_AMT  ,  0  AS  NONPY_AMT  FROM  GIBU.TBRA_MISU_CHEKWON  WHERE  NONPY_DAY  BETWEEN  :FIRST_DAY  ||  '01'   \n";
        query +=" AND  :LAST_DAY  ||  '31'  GROUP  BY  BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  BRAN_CD  ,  0  AS  BEFORE_NONPY_AMT  ,  0  AS  DEMD_AMT  ,  0  AS  REPT_AMT  ,  0  AS  ADJ_AMT  ,  SUM(NONPY_AMT)  AS  NONPY_AMT  FROM  GIBU.TBRA_MISU_CHEKWON  WHERE  NONPY_DAY  =  :LAST_DAY  GROUP  BY  BRAN_CD  )  GROUP  BY  BRAN_CD  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("LAST_DAY", LAST_DAY);               //������
        sobj.setString("FIRST_DAY", FIRST_DAY);               //������
        return sobj;
    }
    // ���� ������
    public DOBJ CALLget_stat_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLget_stat_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_stat_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LAST_DAY = dobj.getRetObject("SEL1").getRecord().get("LAST_DAY");   //������
        String   FIRST_DAY = dobj.getRetObject("SEL1").getRecord().get("FIRST_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :FIRST_DAY  AS  FIRST_DAY  ,  :LAST_DAY  AS  LAST_DAY  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("LAST_DAY", LAST_DAY);               //������
        sobj.setString("FIRST_DAY", FIRST_DAY);               //������
        return sobj;
    }
    //##**$$get_stat
    //##**$$end
}
