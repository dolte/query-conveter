
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s06_t
{
    public bra04_s06_t()
    {
    }
    //##**$$auto_matching_imsi
    /*
    */
    public DOBJ CTLauto_matching_imsi(DOBJ dobj)
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
            dobj  = CALLauto_matching_imsi_SEL1(Conn, dobj);           //  ����û���� ȹ��
            dobj  = CALLauto_matching_imsi_SEL2(Conn, dobj);           //  �̸��˻�
            dobj  = CALLauto_matching_imsi_MPD4(Conn, dobj);           //  �б�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLauto_matching_imsi_MRG9( dobj);        //  �����
            if( dobj.getRetObject("MRG9").getRecordCnt() == 1)
            {
                dobj  = CALLauto_matching_imsi_SEL25(Conn, dobj);           //  ���/���/�ڵ���üȮ��
                if( dobj.getRetObject("SEL25").getRecord().getDouble("CNT") > 0)
                {
                    dobj  = CALLauto_matching_imsi_SEL11(Conn, dobj);           //  ����
                }
                else
                {
                    dobj  = CALLauto_matching_imsi_SEL28(Conn, dobj);           //  ���/���/�ڵ���ü����Ʈ
                }
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
    public DOBJ CTLauto_matching_imsi( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_matching_imsi_SEL1(Conn, dobj);           //  ����û���� ȹ��
        dobj  = CALLauto_matching_imsi_SEL2(Conn, dobj);           //  �̸��˻�
        dobj  = CALLauto_matching_imsi_MPD4(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLauto_matching_imsi_MRG9( dobj);        //  �����
        if( dobj.getRetObject("MRG9").getRecordCnt() == 1)
        {
            dobj  = CALLauto_matching_imsi_SEL25(Conn, dobj);           //  ���/���/�ڵ���üȮ��
            if( dobj.getRetObject("SEL25").getRecord().getDouble("CNT") > 0)
            {
                dobj  = CALLauto_matching_imsi_SEL11(Conn, dobj);           //  ����
            }
            else
            {
                dobj  = CALLauto_matching_imsi_SEL28(Conn, dobj);           //  ���/���/�ڵ���ü����Ʈ
            }
        }
        return dobj;
    }
    // ����û���� ȹ��
    public DOBJ CALLauto_matching_imsi_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_matching_imsi_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_matching_imsi_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR ";
        sobj.setSql(query);
        return sobj;
    }
    // �̸��˻�
    public DOBJ CALLauto_matching_imsi_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_matching_imsi_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_matching_imsi_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPTPRES = dobj.getRetObject("S").getRecord().get("REPTPRES");   //�Ա���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  BRAN_CD  ,  MNGEMSTR_NM  ,  PERMMSTR_NM  FROM  GIBU.TBRA_UPSO  WHERE  (MNGEMSTR_NM  =  SUBSTR(:REPTPRES,  1,3)   \n";
        query +=" OR  PERMMSTR_NM  =  SUBSTR(:REPTPRES,  1,3))   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("REPTPRES", REPTPRES);               //�Ա���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �б�
    public DOBJ CALLauto_matching_imsi_MPD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD4");
        VOBJ dvobj = dobj.getRetObject("SEL2");         //�̸��˻����� ������Ų OBJECT�Դϴ�.(CALLauto_matching_imsi_SEL2)
        dvobj.Println("MPD4");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_matching_imsi_SEL6(Conn, dobj);           //  ��ȸ
                dobj  = CALLauto_matching_imsi_SEL29(Conn, dobj);           //  ���/���/�ڵ���üȮ��
                if( dobj.getRetObject("SEL6").getRecordCnt() > 0 && dobj.getRetObject("SEL29").getRecord().getDouble("CNT") == 1)
                {
                    dobj  = CALLauto_matching_imsi_XIUD17(Conn, dobj);           //  ȹ���� �����ڵ� ����
                    dobj  = CALLauto_matching_imsi_ADD7( dobj);        //  �����
                }
                else if( dobj.getRetObject("SEL6").getRecordCnt() > 0 && dobj.getRetObject("SEL29").getRecord().getDouble("CNT") < 1)
                {
                    dobj  = CALLauto_matching_imsi_ADD18( dobj);        //  ADD
                }
            }
        }
        return dobj;
    }
    // ��ȸ
    public DOBJ CALLauto_matching_imsi_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_matching_imsi_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_matching_imsi_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("SEL1").getRecord().get("DEMD_YRMN");   //û�� ���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   DEMD_AMT = dobj.getRetObject("S").getRecord().getDouble("REPT_AMT");   //û�� �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" (  SELECT  A.UPSO_CD  FROM  GIBU.TBRA_DEMD_OCR  A  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  A.TOT_DEMD_AMT  =  :DEMD_AMT  MINUS   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :DEMD_YRMN  )  UNION   \n";
        query +=" SELECT  UPSO_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :DEMD_YRMN,  :DEMD_YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :DEMD_YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  WHERE  GIBU.FT_SPLIT(DEMDS,  ',',  8)  +  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  =  :DEMD_AMT ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("DEMD_AMT", DEMD_AMT);               //û�� �ݾ�
        return sobj;
    }
    // ���/���/�ڵ���üȮ��
    // 1:�
    public DOBJ CALLauto_matching_imsi_SEL29(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL29");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL29");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_matching_imsi_SEL29(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL29");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_matching_imsi_SEL29(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM  (   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  CLSBS_YRMN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  *  -1  AS  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N'  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  *  -1  AS  CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ȹ���� �����ڵ� ����
    public DOBJ CALLauto_matching_imsi_XIUD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD17");
        VOBJ dvobj = dobj.getRetObject("SEL6");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_matching_imsi_XIUD17(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD17");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_matching_imsi_XIUD17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL6").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_TRANS  \n";
        query +=" SET AUTO_UPSO = AUTO_UPSO || :UPSO_CD || ','  \n";
        query +=" WHERE REPT_DAY = :REPT_DAY  \n";
        query +=" AND REPT_NUM = :REPT_NUM";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����
    public DOBJ CALLauto_matching_imsi_ADD7(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD7");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL6");          //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("ADD7");
        rvobj = wutil.getAddedVobj(dobj,"ADD7","UPSO_CD", dvobj );
        rvobj.setName("ADD7");
        rvobj.Println("ADD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ADD
    public DOBJ CALLauto_matching_imsi_ADD18(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD18");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL6");          //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        rvobj = wutil.getAddedVobj(dobj,"ADD18","UPSO_CD", dvobj );
        rvobj.setName("ADD18");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // �����
    public DOBJ CALLauto_matching_imsi_MRG9(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG9");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"ADD7, ADD18","");
        rvobj.setName("MRG9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ���/���/�ڵ���üȮ��
    // 1:�
    public DOBJ CALLauto_matching_imsi_SEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL25");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL25");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_matching_imsi_SEL25(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_matching_imsi_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("MRG9").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM  (   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  CLSBS_YRMN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  *  -1  AS  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N'  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  *  -1  AS  CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ����
    public DOBJ CALLauto_matching_imsi_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_matching_imsi_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.Println("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_matching_imsi_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("MRG9").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  UPSO_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���/���/�ڵ���ü����Ʈ
    public DOBJ CALLauto_matching_imsi_SEL28(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL28");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL28");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_matching_imsi_SEL28(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL28");
        rvobj.Println("SEL28");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_matching_imsi_SEL28(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("MRG9").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  UPSO_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$auto_matching_imsi
    //##**$$end
}
