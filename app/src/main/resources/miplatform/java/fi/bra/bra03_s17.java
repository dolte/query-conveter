
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s17
{
    public bra03_s17()
    {
    }
    //##**$$upload_file
    /*
    */
    public DOBJ CTLupload_file(DOBJ dobj)
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
            dobj  = CALLupload_file_DEL1(Conn, dobj);           //  �ߺ������� ����
            dobj  = CALLupload_file_MIUD6(Conn, dobj);           //  �б�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupload_file_XIUD7(Conn, dobj);           //  Ŀ��
            dobj  = CALLupload_file_SEL3(Conn, dobj);           //  ������ȸ
            dobj  = CALLupload_file_SEL4(Conn, dobj);           //  ������� ��ȸ
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
    public DOBJ CTLupload_file( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupload_file_DEL1(Conn, dobj);           //  �ߺ������� ����
        dobj  = CALLupload_file_MIUD6(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupload_file_XIUD7(Conn, dobj);           //  Ŀ��
        dobj  = CALLupload_file_SEL3(Conn, dobj);           //  ������ȸ
        dobj  = CALLupload_file_SEL4(Conn, dobj);           //  ������� ��ȸ
        return dobj;
    }
    // �ߺ������� ����
    public DOBJ CALLupload_file_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_file_DEL1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dvobj.getRecord().get("PROC_DAY");   //ó�� ����
        String   GBN = dvobj.getRecord().get("GBN");   //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_DEMD_CARD_APPRV_LOG  \n";
        query +=" where GBN=:GBN  \n";
        query +=" and PROC_DAY=:PROC_DAY";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("GBN", GBN);               //����
        return sobj;
    }
    // �б�
    public DOBJ CALLupload_file_MIUD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD6");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupload_file_INS2(Conn, dobj);           //  �α׵��
            }
        }
        return dobj;
    }
    // �α׵��
    public DOBJ CALLupload_file_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_file_INS2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //���� ��ȣ
        String   PROC_DAY = dvobj.getRecord().get("PROC_DAY");   //ó�� ����
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //ī�� ��ȣ
        String   CLIENT_NUM = dvobj.getRecord().get("CLIENT_NUM");   //�� ��ȣ
        String   CONTENT = dvobj.getRecord().get("CONTENT");   //�Խù� ����
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //�� û�� �ݾ�
        String   APPRV_GBN = dvobj.getRecord().get("APPRV_GBN");   //���� ����
        String   GBN = dvobj.getRecord().get("GBN");   //����
        String   TM = dvobj.getRecord().get("TM");   //�ð�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_DEMD_CARD_APPRV_LOG (TM, GBN, APPRV_GBN, TOT_DEMD_AMT, CONTENT, CLIENT_NUM, CARD_NUM, SEQ, PROC_DAY, APPRV_NUM)  \n";
        query +=" values(:TM , :GBN , :APPRV_GBN , :TOT_DEMD_AMT , :CONTENT , :CLIENT_NUM , :CARD_NUM , :SEQ , :PROC_DAY , :APPRV_NUM )";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //���� ��ȣ
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setString("CLIENT_NUM", CLIENT_NUM);               //�� ��ȣ
        sobj.setString("CONTENT", CONTENT);               //�Խù� ����
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //�� û�� �ݾ�
        sobj.setString("APPRV_GBN", APPRV_GBN);               //���� ����
        sobj.setString("GBN", GBN);               //����
        sobj.setString("TM", TM);               //�ð�
        return sobj;
    }
    // Ŀ��
    public DOBJ CALLupload_file_XIUD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD7");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_file_XIUD7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_XIUD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" commit ";
        sobj.setSql(query);
        return sobj;
    }
    // ������ȸ
    // ������
    public DOBJ CALLupload_file_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupload_file_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S1").getRecord().get("DEMD_YRMN");   //û�� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SS.BRAN_CD  ,  SS.UPSO_CD  ,  SS.UPSO_NM  ,  SS.CARD_GBN  ,  SS.CARD_NUM  ,  SS.EXPIRE_DAY  ,  SS.MNGEMSTR_NM  ,  SS.UPSO_GRAD  ,  SS.TOT_DEMD_AMT  ,  SS.BANK_NM  ,  SS.DEPT_NM  ,  SS.RECEPTION_GBN  ,  TT.APPRV_GBN  ,  TT.APPRV_NUM  ,  SS.RESINUM  ,  SS.DEMD_MMCNT  FROM  GIBU.TBRA_DEMD_CARD_APPRV_LOG  TT  ,(SELECT  XC.BRAN_CD  ,  XB.UPSO_CD  ,  XC.UPSO_NM  ,  XB.CARD_GBN  ,  XB.CARD_NUM  ,  XB.EXPIRE_DAY  ,  XC.MNGEMSTR_NM  ,  XA.BSTYP_CD  ||  XA.UPSO_GRAD  UPSO_GRAD  ,  XA.TOT_DEMD_AMT  ,  DECODE(XB.CARD_GBN,  'WIN','�Ｚī��','LGC','����ī��')  BANK_NM  ,  XF.DEPT_NM  ,  XB.RECEPTION_GBN  ,  XC.CLIENT_NUM  ,  XB.RESINUM  ,  XA.DEMD_MMCNT  FROM  GIBU.TBRA_DEMD_CARD  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.CARD_GBN  ,  A.CARD_NUM  ,  A.EXPIRE_DAY  ,  A.RESINUM  ,  A.RECEPTION_GBN  FROM  GIBU.TBRA_UPSO_AUTO_TEST2  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(AUTO_NUM)  AUTO_NUM  FROM  GIBU.TBRA_UPSO_AUTO_TEST2  WHERE  TERM_YN  =  'N'  GROUP  BY  UPSO_CD  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.AUTO_NUM  =  B.AUTO_NUM  )  XB  ,  GIBU.TBRA_UPSO  XC  ,  INSA.TCPM_DEPT  XF  WHERE  XA.DEMD_YRMN  =  SUBSTR(:DEMD_YRMN,1,6)   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XF.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XA.DEMD_GBN  =  '40'   \n";
        query +=" AND  XA.TOT_DEMD_AMT  >  0  )  SS  WHERE  TRIM(TT.CLIENT_NUM)  =  SS.CLIENT_NUM   \n";
        query +=" AND  TT.TOT_DEMD_AMT  =  SS.TOT_DEMD_AMT   \n";
        query +=" AND  TT.APPRV_GBN  =  '0000'   \n";
        query +=" AND  TT.APPRV_NUM  IS  NOT  NULL   \n";
        query +=" AND  TT.GBN  =  'R' ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        return sobj;
    }
    // ������� ��ȸ
    // �������
    public DOBJ CALLupload_file_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupload_file_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S1").getRecord().get("DEMD_YRMN");   //û�� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SS.BRAN_CD  ,  SS.UPSO_CD  ,  SS.UPSO_NM  ,  SS.CARD_GBN  ,  SS.CARD_NUM  ,  SS.EXPIRE_DAY  ,  SS.MNGEMSTR_NM  ,  SS.UPSO_GRAD  ,  SS.TOT_DEMD_AMT  ,  SS.BANK_NM  ,  SS.DEPT_NM  ,  SS.RECEPTION_GBN  ,  TT.APPRV_GBN  ,  TT.APPRV_NUM  FROM  GIBU.TBRA_DEMD_CARD_APPRV_LOG  TT  ,(SELECT  XC.BRAN_CD  ,  XB.UPSO_CD  ,  XC.UPSO_NM  ,  XB.CARD_GBN  ,  XB.CARD_NUM  ,  XB.EXPIRE_DAY  ,  XC.MNGEMSTR_NM  ,  XA.BSTYP_CD  ||  XA.UPSO_GRAD  UPSO_GRAD  ,  XA.TOT_DEMD_AMT  ,  DECODE(XB.CARD_GBN,  'WIN','�Ｚī��','LGC','����ī��')  BANK_NM  ,  XF.DEPT_NM  ,  XB.RECEPTION_GBN  ,  XC.CLIENT_NUM  FROM  GIBU.TBRA_DEMD_CARD  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.CARD_GBN  ,  A.CARD_NUM  ,  A.EXPIRE_DAY  ,  A.RESINUM  ,  A.RECEPTION_GBN  FROM  GIBU.TBRA_UPSO_AUTO_TEST2  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(AUTO_NUM)  AUTO_NUM  FROM  GIBU.TBRA_UPSO_AUTO_TEST2  WHERE  TERM_YN  =  'N'  GROUP  BY  UPSO_CD  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.AUTO_NUM  =  B.AUTO_NUM  )  XB  ,  GIBU.TBRA_UPSO  XC  ,  INSA.TCPM_DEPT  XF  WHERE  XA.DEMD_YRMN  =  SUBSTR(:DEMD_YRMN,1,6)   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XF.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XA.DEMD_GBN  =  '40'   \n";
        query +=" AND  XA.TOT_DEMD_AMT  >  0  )  SS  WHERE  TRIM(TT.CLIENT_NUM)  =  SS.CLIENT_NUM   \n";
        query +=" AND  TT.TOT_DEMD_AMT  =  SS.TOT_DEMD_AMT   \n";
        query +=" AND  TT.APPRV_GBN  <>  '0000'   \n";
        query +=" AND  TT.GBN  =  'R' ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        return sobj;
    }
    //##**$$upload_file
    //##**$$end
}
