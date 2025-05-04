
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s30
{
    public bra01_s30()
    {
    }
    //##**$$router_search
    /*
    */
    public DOBJ CTLrouter_search(DOBJ dobj)
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
            dobj  = CALLrouter_search_SEL1(Conn, dobj);           //  ����Ͱ�����ȸ
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
    public DOBJ CTLrouter_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrouter_search_SEL1(Conn, dobj);           //  ����Ͱ�����ȸ
        return dobj;
    }
    // ����Ͱ�����ȸ
    // ����Ͱ�����ȸ
    public DOBJ CALLrouter_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   PROC_STS = dobj.getRetObject("S").getRecord().get("PROC_STS");   //ó������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.SERIAL_NO, A.SERIAL_NO_SEQ, A.BRAN_CD, GIBU.GET_BRAN_NM(A.BRAN_CD) BRAN_NM, A.UPSO_CD, B.UPSO_NM, A.BSTYP_CD, GIBU.FT_GET_BSTYP_NM(A.BSTYP_CD) BSTYP_NM, NVL(B.UPSO_NEW_ADDR1,B.UPSO_ADDR1) UPSO_ADDR, A.BSCON_CD, FIDU.GET_BSCON_NM(A.BSCON_CD) BSCON_NM, A.ROOM_NUM, A.ACMCN_CD, A.ROUTER_CD, B.STAFF_CD, FIDU.GET_STAFF_NM(B.STAFF_CD) STAFF_NM, B.CLSBS_YRMN CLSBS_YRMN, (  ";
        query +=" SELECT MAX(OCC_DAY)  ";
        query +=" FROM GIBU.TBRA_OFF_LOGDATA X  ";
        query +=" WHERE 1=1  ";
        query +=" AND X.ACMCN_CD = A.ACMCN_CD) AS LAST_REC_DAY, NVL((SELECT DISTINCT 1  ";
        query +=" FROM GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  ";
        query +=" WHERE SERIAL_NO = A.SERIAL_NO  ";
        query +=" AND SERIAL_NO_SEQ = DECODE(C.SERIAL_NO,'',A.SERIAL_NO_SEQ, TO_NUMBER(A.SERIAL_NO_SEQ)+1)) ,'0') ATTACH_YN, A.DSCT_YN, A.DSCT_START, A.DSCT_END, A.CHG_GBN, CASE WHEN C.SERIAL_NO IS NOT NULL  ";
        query +=" AND A.PROC_STS ='M' THEN 'N' ELSE A.PROC_STS END PROC_STS, A.REMAK_CHG, A.REMAK_CHG_D, CASE WHEN C.SERIAL_NO IS NOT NULL  ";
        query +=" AND A.PROC_STS ='M' THEN TO_CHAR(C.BRAN_CONFIRM_DATE,'yyyymmdd') ELSE TO_CHAR(A.BRAN_CONFIRM_DATE,'yyyymmdd') END BRAN_CONFIRM_DATE, CASE WHEN C.SERIAL_NO IS NOT NULL  ";
        query +=" AND A.PROC_STS ='M' THEN C.BRAN_CONFIRM_ID ELSE A.BRAN_CONFIRM_ID END BRAN_CONFIRM_ID, CASE WHEN C.SERIAL_NO IS NOT NULL  ";
        query +=" AND A.PROC_STS ='M' THEN TO_CHAR(C.MNG_CONFIRM_DATE,'yyyymmdd') ELSE TO_CHAR(A.MNG_CONFIRM_DATE,'yyyymmdd') END MNG_CONFIRM_DATE, CASE WHEN C.SERIAL_NO IS NOT NULL  ";
        query +=" AND A.PROC_STS ='M' THEN C.MNG_CONFIRM_ID ELSE A.MNG_CONFIRM_ID END MNG_CONFIRM_ID, CASE WHEN CLSBS_YRMN IS NOT NULL THEN '1' WHEN NVL((SELECT MAX(OCC_DAY)  ";
        query +=" FROM GIBU.TBRA_OFF_LOGDATA X  ";
        query +=" WHERE 1=1  ";
        query +=" AND X.ACMCN_CD = A.ACMCN_CD),'29991231') <= TO_CHAR(SYSDATE-7,'yyyymmdd') THEN '2' WHEN TO_CHAR(ADD_MONTHS(NVL(A.MNG_CONFIRM_DATE,'29991231'),47),'yyyymm') <=TO_CHAR(SYSDATE,'YYYYMM') THEN '3' ELSE '0' END REP_OBJ  ";
        query +=" FROM GIBU.TBRA_OFF_ROUTER_MNG A, GIBU.TBRA_UPSO B, (SELECT SERIAL_NO, PROC_STS, BRAN_CONFIRM_DATE, BRAN_CONFIRM_ID, MNG_CONFIRM_DATE, MNG_CONFIRM_ID  ";
        query +=" FROM GIBU.TBRA_OFF_ROUTER_MNG  ";
        query +=" WHERE 1=1  ";
        query +=" AND PROC_STS='N' ) C  ";
        query +=" WHERE 1=1  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.SERIAL_NO = C.SERIAL_NO(+)  ";
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.PROC_STS <>'N'  ";
        if( !PROC_STS.equals("") )
        {
            query +=" AND A.PROC_STS =:PROC_STS  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND B.STAFF_CD =:STAFF_CD  ";
        }
        query +=" ORDER BY BRAN_CD, UPSO_CD  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        }
        if(!PROC_STS.equals(""))
        {
            sobj.setString("PROC_STS", PROC_STS);               //ó������
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$router_search
    //##**$$router_upso_info
    /*
    */
    public DOBJ CTLrouter_upso_info(DOBJ dobj)
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
            dobj  = CALLrouter_upso_info_SEL1(Conn, dobj);           //  �������� ��ȸ
            dobj  = CALLrouter_upso_info_SEL2(Conn, dobj);           //  ��ġ�̷�
            dobj  = CALLrouter_upso_info_SEL3(Conn, dobj);           //  ���ֱ�������ȸ
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
    public DOBJ CTLrouter_upso_info( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrouter_upso_info_SEL1(Conn, dobj);           //  �������� ��ȸ
        dobj  = CALLrouter_upso_info_SEL2(Conn, dobj);           //  ��ġ�̷�
        dobj  = CALLrouter_upso_info_SEL3(Conn, dobj);           //  ���ֱ�������ȸ
        return dobj;
    }
    // �������� ��ȸ
    // �������� ��ȸ
    public DOBJ CALLrouter_upso_info_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_upso_info_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_upso_info_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD,  UPSO_NM,  BRAN_CD,  STAFF_CD,  A.UPSO_NEW_ADDR1  UPSO_ADDR,  FIDU.GET_STAFF_NM(STAFF_CD)  STAFF_NM,  GIBU.GET_BRAN_NM(BRAN_CD)  BRAN_NM,  GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD)  BSTYP_CD,  GIBU.FT_GET_BSTYP_NM(GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD))  BSTYP_NM  FROM  GIBU.TBRA_UPSO  A  WHERE  1=1   \n";
        query +=" AND  UPSO_CD  =:UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��ġ�̷�
    public DOBJ CALLrouter_upso_info_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_upso_info_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_upso_info_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("SEL1").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(CNT)  CNT  FROM  (   \n";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_OFF_ROUTER_MNG  WHERE  1=1   \n";
        query +=" AND  UPSO_CD=:UPSO_CD   \n";
        query +=" AND  PROC_STS<>'N'  UNION  ALL   \n";
        query +=" SELECT  COUNT(*)  CNT  FROM  LOG.KDS_SHOPROOM  WHERE  1=1   \n";
        query +=" AND  UPSO_CD  =:UPSO_CD  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ֱ�������ȸ
    // ���ֱ�������ȸ
    public DOBJ CALLrouter_upso_info_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_upso_info_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_upso_info_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(MCHN_COMPY)  ACM_CNT  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A  ,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  1=1   \n";
        query +=" AND  A.MODEL_CD=  B.MODEL_CD   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.MCHN_COMPY  =  :BSCON_CD   \n";
        query +=" AND  A.ONOFF_GBN='F' ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    //##**$$router_upso_info
    //##**$$router_file_search
    /*
    */
    public DOBJ CTLrouter_file_search(DOBJ dobj)
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
            dobj  = CALLrouter_file_search_SEL1(Conn, dobj);           //  ���ϴٿ�ε� ��ȸ
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
    public DOBJ CTLrouter_file_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrouter_file_search_SEL1(Conn, dobj);           //  ���ϴٿ�ε� ��ȸ
        return dobj;
    }
    // ���ϴٿ�ε� ��ȸ
    public DOBJ CALLrouter_file_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_file_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_file_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("S").getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SERIAL_NO_SEQ = dobj.getRetObject("S").getRecord().get("SERIAL_NO_SEQ");   //�ø������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD,SERIAL_NO,SERIAL_NO_SEQ,SVR_FILE_ROUT,  SVR_FILE_NM,  FILE_NM  FROM  GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  A  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD=:UPSO_CD   \n";
        query +=" AND  A.SERIAL_NO=:SERIAL_NO   \n";
        query +=" AND  A.SERIAL_NO_SEQ=:SERIAL_NO_SEQ  ORDER  BY  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        return sobj;
    }
    //##**$$router_file_search
    //##**$$router_n_del
    /*
    */
    public DOBJ CTLrouter_nm_del(DOBJ dobj)
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
            dobj  = CALLrouter_n_del_SEL4(Conn, dobj);           //  SEL
            dobj  = CALLrouter_n_del_DEL2(Conn, dobj);           //  ÷������ ����(�̸�Ī)
            dobj  = CALLrouter_n_del_XIUD1(Conn, dobj);           //  �Է³��� ����(�̸�Ī)
            dobj  = CALLrouter_n_del_XIUD5(Conn, dobj);           //  �Է³��� ����(��Ī)
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
    public DOBJ CTLrouter_nm_del( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrouter_n_del_SEL4(Conn, dobj);           //  SEL
        dobj  = CALLrouter_n_del_DEL2(Conn, dobj);           //  ÷������ ����(�̸�Ī)
        dobj  = CALLrouter_n_del_XIUD1(Conn, dobj);           //  �Է³��� ����(�̸�Ī)
        dobj  = CALLrouter_n_del_XIUD5(Conn, dobj);           //  �Է³��� ����(��Ī)
        return dobj;
    }
    // SEL
    public DOBJ CALLrouter_n_del_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_n_del_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_n_del_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("S").getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SERIAL_NO,  SERIAL_NO_SEQ,  UPSO_CD  FROM  GIBU.TBRA_OFF_ROUTER_MNG  WHERE  SERIAL_NO=:SERIAL_NO   \n";
        query +=" AND  PROC_STS='N' ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        return sobj;
    }
    // ÷������ ����(�̸�Ī)
    // ÷������ ����(�̸�Ī)
    public DOBJ CALLrouter_n_del_DEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL2");
        VOBJ dvobj = dobj.getRetObject("SEL4");           //SEL���� ������Ų OBJECT�Դϴ�.(CALLrouter_n_del_SEL4)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_n_del_DEL2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_n_del_DEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO_SEQ = dvobj.getRecord().get("SERIAL_NO_SEQ");   //�ø������
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  \n";
        query +=" where SERIAL_NO=:SERIAL_NO  \n";
        query +=" and SERIAL_NO_SEQ=:SERIAL_NO_SEQ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        return sobj;
    }
    // �Է³��� ����(�̸�Ī)
    // �Է³��� ����(�̸�Ī)
    public DOBJ CALLrouter_n_del_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_n_del_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_n_del_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("SEL4").getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   SERIAL_NO_SEQ = dobj.getRetObject("SEL4").getRecord().get("SERIAL_NO_SEQ");   //�ø������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_OFF_ROUTER_MNG A  \n";
        query +=" WHERE 1=1  \n";
        query +=" AND A.SERIAL_NO=:SERIAL_NO  \n";
        query +=" AND A.SERIAL_NO_SEQ=:SERIAL_NO_SEQ  \n";
        query +=" AND A.PROC_STS ='N'";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        return sobj;
    }
    // �Է³��� ����(��Ī)
    // �Է³��� ����(��Ī)
    public DOBJ CALLrouter_n_del_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_n_del_XIUD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_n_del_XIUD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("S").getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   SERIAL_NO_SEQ = dobj.getRetObject("S").getRecord().get("SERIAL_NO_SEQ");   //�ø������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_OFF_ROUTER_MNG A  \n";
        query +=" SET REMAK_CHG='', REMAK_CHG_D='' , CHG_GBN=''  \n";
        query +=" WHERE 1=1  \n";
        query +=" AND A.SERIAL_NO=:SERIAL_NO  \n";
        query +=" AND A.SERIAL_NO=:SERIAL_NO_SEQ  \n";
        query +=" AND A.PROC_STS ='M'";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        return sobj;
    }
    //##**$$router_n_del
    //##**$$router_n_search
    /*
    */
    public DOBJ CTLrouter_n_search(DOBJ dobj)
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
            dobj  = CALLrouter_n_search_SEL1(Conn, dobj);           //  SEL
            dobj  = CALLrouter_n_search_SEL2(Conn, dobj);           //  ���ϴٿ�ε� ��ȸ
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
    public DOBJ CTLrouter_n_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrouter_n_search_SEL1(Conn, dobj);           //  SEL
        dobj  = CALLrouter_n_search_SEL2(Conn, dobj);           //  ���ϴٿ�ε� ��ȸ
        return dobj;
    }
    // SEL
    public DOBJ CALLrouter_n_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_n_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_n_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("S").getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD,  GIBU.GET_BRAN_NM(A.BRAN_CD)  BRAN_NM,  A.UPSO_CD,  B.UPSO_NM,  A.BSTYP_CD,  GIBU.FT_GET_BSTYP_NM(A.BSTYP_CD)  BSTYP_NM,  NVL(B.UPSO_NEW_ADDR1,B.UPSO_ADDR1)  UPSO_ADDR,  A.BSCON_CD,  FIDU.GET_BSCON_NM(A.BSCON_CD)  BSCON_NM,  A.ROOM_NUM,  A.ACMCN_CD,  A.SERIAL_NO,  A.SERIAL_NO_SEQ,  A.ROUTER_CD,  B.STAFF_CD,  FIDU.GET_STAFF_NM(B.STAFF_CD)  STAFF_NM,  B.CLSBS_YRMN  CLSBS_YRMN,  NVL((SELECT  DISTINCT  1  FROM  GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  WHERE  UPSO_CD=A.UPSO_CD)  ,'0')  ATTACH_YN,  A.PROC_STS,  A.DSCT_YN,  A.DSCT_START,  A.DSCT_END,  A.REMAK_CHG,  A.REMAK_CHG_D,  TO_CHAR(A.BRAN_CONFIRM_DATE,'yyyymmdd')  BRAN_CONFIRM_DATE,  A.BRAN_CONFIRM_ID,  TO_CHAR(A.MNG_CONFIRM_DATE,'yyyymmdd')  MNG_CONFIRM_DATE,  A.MNG_CONFIRM_ID  FROM  GIBU.TBRA_OFF_ROUTER_MNG  A,  GIBU.TBRA_UPSO  B  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD=B.UPSO_CD   \n";
        query +=" AND  A.SERIAL_NO=:SERIAL_NO   \n";
        query +=" AND  PROC_STS='N' ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        return sobj;
    }
    // ���ϴٿ�ε� ��ȸ
    public DOBJ CALLrouter_n_search_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_n_search_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_n_search_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("SEL1").getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL1").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SERIAL_NO_SEQ = dobj.getRetObject("SEL1").getRecord().get("SERIAL_NO_SEQ");   //�ø������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SERIAL_NO,  SERIAL_NO_SEQ,  UPSO_CD,SVR_FILE_ROUT,  SVR_FILE_NM,  FILE_NM  FROM  GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  A  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD=:UPSO_CD   \n";
        query +=" AND  A.SERIAL_NO  =:SERIAL_NO   \n";
        query +=" AND  A.SERIAL_NO_SEQ  =:SERIAL_NO_SEQ  ORDER  BY  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        return sobj;
    }
    //##**$$router_n_search
    //##**$$router_n_save
    /*
    */
    public DOBJ CTLrouter_n_save(DOBJ dobj)
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
            dobj  = CALLrouter_n_save_MIUD2(Conn, dobj);           //  ����ͽ�û��������
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLrouter_n_save_XIUD13(Conn, dobj);           //  bef���Һ���
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
    public DOBJ CTLrouter_n_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrouter_n_save_MIUD2(Conn, dobj);           //  ����ͽ�û��������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLrouter_n_save_XIUD13(Conn, dobj);           //  bef���Һ���
        return dobj;
    }
    // ����ͽ�û��������
    public DOBJ CALLrouter_n_save_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrouter_n_save_SEL2(Conn, dobj);           //  ����
                dobj  = CALLrouter_n_save_INS6(Conn, dobj);           //  ����� ��û�̷� �Է�
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrouter_n_save_UPD7(Conn, dobj);           //  ����� ��û�̷� ������Ʈ
            }
        }
        return dobj;
    }
    // ����
    public DOBJ CALLrouter_n_save_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_n_save_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_n_save_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("R").getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(SERIAL_NO_SEQ)  +1  SERIAL_NO_SEQ  FROM  GIBU.TBRA_OFF_ROUTER_MNG  WHERE  1=1   \n";
        query +=" AND  SERIAL_NO=:SERIAL_NO ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        return sobj;
    }
    // ����� ��û�̷� ������Ʈ
    // ����� ��û�̷� ������Ʈ
    public DOBJ CALLrouter_n_save_UPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_n_save_UPD7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_n_save_UPD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   DSCT_START = dvobj.getRecord().get("DSCT_START");   //���ν��ۿ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   ROUTER_CD = dvobj.getRecord().get("ROUTER_CD");
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   SERIAL_NO_SEQ = dvobj.getRecord().get("SERIAL_NO_SEQ");   //�ø������
        String   ROOM_NUM = dvobj.getRecord().get("ROOM_NUM");
        String   ACMCN_CD = dvobj.getRecord().get("ACMCN_CD");   //���ֱ� �ڵ�
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   DSCT_YN = dvobj.getRecord().get("DSCT_YN");   //�������뿩��
        String   DSCT_END = dvobj.getRecord().get("DSCT_END");   //���������
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   PROC_STS ="N";   //ó������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_OFF_ROUTER_MNG  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , DSCT_END=:DSCT_END , DSCT_YN=:DSCT_YN , BSTYP_CD=:BSTYP_CD , ACMCN_CD=:ACMCN_CD , ROOM_NUM=:ROOM_NUM , BSCON_CD=:BSCON_CD , ROUTER_CD=:ROUTER_CD , DSCT_START=:DSCT_START , MOD_DATE=SYSDATE , BRAN_CD=:BRAN_CD  \n";
        query +=" where PROC_STS=:PROC_STS  \n";
        query +=" and SERIAL_NO_SEQ=:SERIAL_NO_SEQ  \n";
        query +=" and SERIAL_NO=:SERIAL_NO  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("DSCT_START", DSCT_START);               //���ν��ۿ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("ROUTER_CD", ROUTER_CD);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        sobj.setString("ROOM_NUM", ROOM_NUM);
        sobj.setString("ACMCN_CD", ACMCN_CD);               //���ֱ� �ڵ�
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("DSCT_YN", DSCT_YN);               //�������뿩��
        sobj.setString("DSCT_END", DSCT_END);               //���������
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("PROC_STS", PROC_STS);               //ó������
        return sobj;
    }
    // ����� ��û�̷� �Է�
    // ����� ��û�̷� �Է�
    public DOBJ CALLrouter_n_save_INS6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_n_save_INS6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_n_save_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   DSCT_START = dvobj.getRecord().get("DSCT_START");   //���ν��ۿ�
        String   REMAK_CHG_D = dvobj.getRecord().get("REMAK_CHG_D");
        String   ROUTER_CD = dvobj.getRecord().get("ROUTER_CD");
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   REMAK_CHG = dvobj.getRecord().get("REMAK_CHG");
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   ROOM_NUM = dvobj.getRecord().get("ROOM_NUM");
        String   ACMCN_CD = dvobj.getRecord().get("ACMCN_CD");   //���ֱ� �ڵ�
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   DSCT_YN = dvobj.getRecord().get("DSCT_YN");   //�������뿩��
        String   DSCT_END = dvobj.getRecord().get("DSCT_END");   //���������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PROC_STS ="N";   //ó������
        String   SERIAL_NO_SEQ = dobj.getRetObject("SEL2").getRecord().get("SERIAL_NO_SEQ");   //�ø������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_OFF_ROUTER_MNG (INSPRES_ID, DSCT_END, PROC_STS, DSCT_YN, BSTYP_CD, ACMCN_CD, ROOM_NUM, SERIAL_NO_SEQ, BSCON_CD, INS_DATE, REMAK_CHG, SERIAL_NO, ROUTER_CD, REMAK_CHG_D, DSCT_START, UPSO_CD, BRAN_CD)  \n";
        query +=" values(:INSPRES_ID , :DSCT_END , :PROC_STS , :DSCT_YN , :BSTYP_CD , :ACMCN_CD , :ROOM_NUM , :SERIAL_NO_SEQ , :BSCON_CD , SYSDATE, :REMAK_CHG , :SERIAL_NO , :ROUTER_CD , :REMAK_CHG_D , :DSCT_START , :UPSO_CD , :BRAN_CD )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("DSCT_START", DSCT_START);               //���ν��ۿ�
        sobj.setString("REMAK_CHG_D", REMAK_CHG_D);
        sobj.setString("ROUTER_CD", ROUTER_CD);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("REMAK_CHG", REMAK_CHG);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("ROOM_NUM", ROOM_NUM);
        sobj.setString("ACMCN_CD", ACMCN_CD);               //���ֱ� �ڵ�
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("DSCT_YN", DSCT_YN);               //�������뿩��
        sobj.setString("DSCT_END", DSCT_END);               //���������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PROC_STS", PROC_STS);               //ó������
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        return sobj;
    }
    // bef���Һ���
    public DOBJ CALLrouter_n_save_XIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD13");
        VOBJ dvobj = dobj.getRetObject("S1");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_n_save_XIUD13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_n_save_XIUD13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   CHG_GBN = dobj.getRetObject("S1").getRecord().get("CHG_GBN");   //���� ����
        String   DSCT_END = dobj.getRetObject("S1").getRecord().get("DSCT_END");   //���������
        String   DSCT_YN = dobj.getRetObject("S1").getRecord().get("DSCT_YN");   //�������뿩��
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   PROC_STS = dobj.getRetObject("S1").getRecord().get("PROC_STS");   //ó������
        String   REMAK_CHG = dobj.getRetObject("S1").getRecord().get("REMAK_CHG");   //REMAK_CHG
        String   REMAK_CHG_D = dobj.getRetObject("S1").getRecord().get("REMAK_CHG_D");   //REMAK_CHG_D
        String   UPSO_CD = dobj.getRetObject("S1").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_OFF_ROUTER_MNG  \n";
        query +=" SET DSCT_END=:DSCT_END ,DSCT_YN=:DSCT_YN ,REMAK_CHG=:REMAK_CHG ,REMAK_CHG_D=:REMAK_CHG_D ,CHG_GBN=:CHG_GBN ,MOD_DATE=SYSDATE ,MODPRES_ID=:MODPRES_ID  \n";
        query +=" WHERE 1=1  \n";
        query +=" AND UPSO_CD =:UPSO_CD  \n";
        query +=" AND PROC_STS =:PROC_STS";
        sobj.setSql(query);
        sobj.setString("CHG_GBN", CHG_GBN);               //���� ����
        sobj.setString("DSCT_END", DSCT_END);               //���������
        sobj.setString("DSCT_YN", DSCT_YN);               //�������뿩��
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("PROC_STS", PROC_STS);               //ó������
        sobj.setString("REMAK_CHG", REMAK_CHG);               //REMAK_CHG
        sobj.setString("REMAK_CHG_D", REMAK_CHG_D);               //REMAK_CHG_D
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$router_n_save
    //##**$$router_conf
    /*
    */
    public DOBJ CTLrouter_conf(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("BRAN"))
            {
                dobj  = CALLrouter_conf_UPD5(Conn, dobj);           //  ���������
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("MNG"))
            {
                dobj  = CALLrouter_conf_XIUD8(Conn, dobj);           //  ������ ���� ��Ī => �α׼��Ŵ��
                dobj  = CALLrouter_conf_XIUD7(Conn, dobj);           //  ������ ���� ��û=>��Ī
                dobj  = CALLrouter_conf_XIUD10(Conn, dobj);           //  �������߰�
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
    public DOBJ CTLrouter_conf( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("BRAN"))
        {
            dobj  = CALLrouter_conf_UPD5(Conn, dobj);           //  ���������
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("MNG"))
        {
            dobj  = CALLrouter_conf_XIUD8(Conn, dobj);           //  ������ ���� ��Ī => �α׼��Ŵ��
            dobj  = CALLrouter_conf_XIUD7(Conn, dobj);           //  ������ ���� ��û=>��Ī
            dobj  = CALLrouter_conf_XIUD10(Conn, dobj);           //  �������߰�
        }
        return dobj;
    }
    // ���������
    // ���������
    public DOBJ CALLrouter_conf_UPD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_conf_UPD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_conf_UPD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BRAN_CONFIRM_DATE ="";        //������ ������
        String   SERIAL_NO_SEQ = dvobj.getRecord().get("SERIAL_NO_SEQ");   //�ø������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   PROC_STS = dvobj.getRecord().get("PROC_STS");   //ó������
        String   BRAN_CONFIRM_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //���� ������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_OFF_ROUTER_MNG  \n";
        query +=" set SERIAL_NO=:SERIAL_NO , BRAN_CONFIRM_ID=:BRAN_CONFIRM_ID , SERIAL_NO_SEQ=:SERIAL_NO_SEQ , BRAN_CONFIRM_DATE=SYSDATE  \n";
        query +=" where PROC_STS=:PROC_STS  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("PROC_STS", PROC_STS);               //ó������
        sobj.setString("BRAN_CONFIRM_ID", BRAN_CONFIRM_ID);               //���� ������
        return sobj;
    }
    // ������ ���� ��Ī => �α׼��Ŵ��
    // ������ ���� ��Ī => �α׼��Ŵ��
    public DOBJ CALLrouter_conf_XIUD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD8");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_conf_XIUD8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_conf_XIUD8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BEF_STAFF_CD = dobj.getRetObject("S").getRecord().get("BEF_STAFF_CD");   //BEF_STAFF_CD
        String   BEF_UPSO_CD = dobj.getRetObject("S").getRecord().get("BEF_UPSO_CD");   //BEF_UPSO_CD
        String   CHG_GBN = dobj.getRetObject("S").getRecord().get("CHG_GBN");   //���� ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_OFF_ROUTER_MNG  \n";
        query +=" SET PROC_STS='L', STAFF_CD=:BEF_STAFF_CD, CHG_GBN=:CHG_GBN, CONF_DATE=SYSDATE, MOD_DATE=SYSDATE, MODPRES_ID=:MODPRES_ID  \n";
        query +=" WHERE 1=1  \n";
        query +=" AND UPSO_CD=:BEF_UPSO_CD  \n";
        query +=" AND PROC_STS='M'";
        sobj.setSql(query);
        sobj.setString("BEF_STAFF_CD", BEF_STAFF_CD);               //BEF_STAFF_CD
        sobj.setString("BEF_UPSO_CD", BEF_UPSO_CD);               //BEF_UPSO_CD
        sobj.setString("CHG_GBN", CHG_GBN);               //���� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // ������ ���� ��û=>��Ī
    // ������ ���� ��û=>��Ī
    public DOBJ CALLrouter_conf_XIUD7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLrouter_conf_XIUD7(dobj, dvobj);
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
    private SQLObject SQLrouter_conf_XIUD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MNG_CONFIRM_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ �����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_OFF_ROUTER_MNG  \n";
        query +=" SET PROC_STS='M' ,MNG_CONFIRM_DATE = SYSDATE ,MNG_CONFIRM_ID =:MNG_CONFIRM_ID  \n";
        query +=" WHERE 1=1  \n";
        query +=" AND UPSO_CD=:UPSO_CD  \n";
        query +=" AND PROC_STS='N'";
        sobj.setSql(query);
        sobj.setString("MNG_CONFIRM_ID", MNG_CONFIRM_ID);               //������ �����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������߰�
    public DOBJ CALLrouter_conf_XIUD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD10");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_conf_XIUD10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_conf_XIUD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("S").getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   SERIAL_NO_SEQ = dobj.getRetObject("S").getRecord().get("SERIAL_NO_SEQ");   //�ø������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" MERGE  INTO  LOG.KDS_SHOP  A  USING  (SELECT  UPSO_CD,  DSCT_START,  DSCT_END,  DSCT_YN  FROM  GIBU.TBRA_OFF_ROUTER_MNG  X  WHERE  1=1   \n";
        query +=" AND  X.SERIAL_NO=:SERIAL_NO   \n";
        query +=" AND  X.SERIAL_NO_SEQ  =:SERIAL_NO_SEQ)  B  ON  (A.UPSO_CD=B.UPSO_CD  )  WHEN  MATCHED  THEN  UPDATE  SET  A.DSCT_START=  B.DSCT_START,  A.DSCT_END=  B.DSCT_END,  A.DSCT_YN=  B.DSCT_YN  WHEN  NOT  MATCHED  THEN  INSERT  (A.UPSO_CD,A.DSCT_START,A.DSCT_END,A.DSCT_YN)  VALUES  (B.UPSO_CD,B.DSCT_START,B.DSCT_END,B.DSCT_YN) ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        return sobj;
    }
    //##**$$router_conf
    //##**$$router_file_upload
    /*
    */
    public DOBJ CTLrouter_file_upload(DOBJ dobj)
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
            dobj  = CALLrouter_file_upload_SEL_FILE(Conn, dobj);           //  ���������
            dobj  = CALLrouter_file_upload_MIUD1(Conn, dobj);           //  ���Ҽ��� ÷������
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
    public DOBJ CTLrouter_file_upload( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrouter_file_upload_SEL_FILE(Conn, dobj);           //  ���������
        dobj  = CALLrouter_file_upload_MIUD1(Conn, dobj);           //  ���Ҽ��� ÷������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ���������
    public DOBJ CALLrouter_file_upload_SEL_FILE(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL_FILE");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL_FILE");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_file_upload_SEL_FILE(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL_FILE");
        rvobj.Println("SEL_FILE");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_file_upload_SEL_FILE(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DAY ="";        //��� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '/upload_file/GIBU/OFFLINE/'  ||  SUBSTR(TO_CHAR(SYSDATE,'YYYYMM'),  1,  6)  AS  DFILEPATH  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // ���Ҽ��� ÷������
    public DOBJ CALLrouter_file_upload_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrouter_file_upload_SEL18(Conn, dobj);           //  SEQ ���߱�
                dobj  = CALLrouter_file_upload_SEL25(Conn, dobj);           //  ��������ο� ���ϸ�
                dobj  = CALLrouter_file_upload_FUT26( dobj);        //  �����̵�
                dobj  = CALLrouter_file_upload_FUT27( dobj);        //  �����̸��ٲٱ�
                dobj  = CALLrouter_file_upload_INS31(Conn, dobj);           //  ���Ͼ��ε���������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrouter_file_upload_SEL31(Conn, dobj);           //  �������� ��ȸ(�������)
                dobj  = CALLrouter_file_upload_FUT32( dobj);        //  ���ϻ���
                dobj  = CALLrouter_file_upload_SEL33(Conn, dobj);           //  ��������ο� ���ϸ�
                dobj  = CALLrouter_file_upload_FUT34( dobj);        //  �����̵�
                dobj  = CALLrouter_file_upload_FUT35( dobj);        //  �����̸��ٲٱ�
                dobj  = CALLrouter_file_upload_UPD36(Conn, dobj);           //  ���Ͼ��ε����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrouter_file_upload_FUT39( dobj);        //  ���ϻ���
                dobj  = CALLrouter_file_upload_DEL40(Conn, dobj);           //  ��������
            }
        }
        return dobj;
    }
    // ���ϻ���
    public DOBJ CALLrouter_file_upload_FUT39(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT39");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT39");
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            wutil.delete(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString() );
        }
        dvobj.setName("FUT39") ;
        dvobj.Println("FUT39");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �������� ��ȸ(�������)
    public DOBJ CALLrouter_file_upload_SEL31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL31");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL31");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_file_upload_SEL31(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_file_upload_SEL31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("R").getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SERIAL_NO_SEQ = dobj.getRetObject("R").getRecord().get("SERIAL_NO_SEQ");   //�ø������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SVR_FILE_NM  ,  SVR_FILE_ROUT  FROM  GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SERIAL_NO=:SERIAL_NO   \n";
        query +=" AND  SERIAL_NO_SEQ=:SERIAL_NO_SEQ ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        return sobj;
    }
    // SEQ ���߱�
    public DOBJ CALLrouter_file_upload_SEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL18");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL18");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_file_upload_SEL18(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL18");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_file_upload_SEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("R").getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SERIAL_NO_SEQ  FROM  GIBU.TBRA_OFF_ROUTER_MNG  WHERE  1=1   \n";
        query +=" AND  SERIAL_NO=:SERIAL_NO   \n";
        query +=" AND  PROC_STS='N' ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        return sobj;
    }
    // ��������
    public DOBJ CALLrouter_file_upload_DEL40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL40");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL40");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_file_upload_DEL40(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL40") ;
        rvobj.Println("DEL40");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_file_upload_DEL40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO_SEQ = dvobj.getRecord().get("SERIAL_NO_SEQ");   //�ø������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  \n";
        query +=" where SERIAL_NO=:SERIAL_NO  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SERIAL_NO_SEQ=:SERIAL_NO_SEQ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        return sobj;
    }
    // ���ϻ���
    public DOBJ CALLrouter_file_upload_FUT32(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT32");
        VOBJ dvobj = dobj.getRetObject("SEL31");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT32");
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            wutil.delete(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString() );
        }
        dvobj.setName("FUT32") ;
        dvobj.Println("FUT32");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ��������ο� ���ϸ�
    public DOBJ CALLrouter_file_upload_SEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL25");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL25");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_file_upload_SEL25(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL25");
        rvobj.Println("SEL25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_file_upload_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DAY ="";        //��� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SERIAL_NO_SEQ = dobj.getRetObject("SEL18").getRecord().get("SERIAL_NO_SEQ");   //�ø������
        String   FILE_NM = dobj.getRetObject("R").getRecord().get("FILE_NM");   //���� ��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  ||  :UPSO_CD  ||  '-'  ||  TO_CHAR(SYSDATE,'yyyymmdd')  ||  '-'  ||  :SERIAL_NO_SEQ  ||  '-'  ||  TO_CHAR  (SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR  (  :FILE_NM,  INSTR  (  :FILE_NM,  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        return sobj;
    }
    // �����̵�
    public DOBJ CALLrouter_file_upload_FUT26(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT26");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT26");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT26") ;
        dvobj.Println("FUT26");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ��������ο� ���ϸ�
    public DOBJ CALLrouter_file_upload_SEL33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL33");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL33");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrouter_file_upload_SEL33(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL33");
        rvobj.Println("SEL33");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_file_upload_SEL33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_DAY = dobj.getRetObject("R").getRecord().get("INS_DAY");   //��� ����
        String   SERIAL_NO_SEQ = dobj.getRetObject("R").getRecord().get("SERIAL_NO_SEQ");   //�ø������
        String   FILE_NM = dobj.getRetObject("R").getRecord().get("FILE_NM");   //���� ��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  ||  :UPSO_CD  ||  '-'  ||  :INS_DAY  ||  '-'  ||  :SERIAL_NO_SEQ  ||  '-'  ||  TO_CHAR  (SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR  (  :FILE_NM,  INSTR  (  :FILE_NM,  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        return sobj;
    }
    // �����̸��ٲٱ�
    public DOBJ CALLrouter_file_upload_FUT27(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT27");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT27");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dobj.getRetObject("SEL25").getRecord().get("DFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.rename(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILENAME").toString());
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT27") ;
        dvobj.Println("FUT27");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �����̵�
    public DOBJ CALLrouter_file_upload_FUT34(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT34");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT34");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT34") ;
        dvobj.Println("FUT34");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLrouter_file_upload_INS31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS31");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS31");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_file_upload_INS31(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS31") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_file_upload_INS31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   FILE_NM = dvobj.getRecord().get("FILE_NM");   //���� ��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   SERIAL_NO_SEQ = dobj.getRetObject("SEL18").getRecord().get("SERIAL_NO_SEQ");   //�ø������
        String   SVR_FILE_NM = dobj.getRetObject("SEL25").getRecord().get("DFILENAME");   //���� ���� ��
        String   SVR_FILE_ROUT = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");   //���� ���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_OFF_ROUTER_MNG_ATTACH (INS_DATE, INSPRES_ID, SERIAL_NO, UPSO_CD, SVR_FILE_ROUT, SVR_FILE_NM, SERIAL_NO_SEQ, REMAK, FILE_NM)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :SERIAL_NO , :UPSO_CD , :SVR_FILE_ROUT , :SVR_FILE_NM , :SERIAL_NO_SEQ , :REMAK , :FILE_NM )";
        sobj.setSql(query);
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        sobj.setString("SVR_FILE_NM", SVR_FILE_NM);               //���� ���� ��
        sobj.setString("SVR_FILE_ROUT", SVR_FILE_ROUT);               //���� ���� ���
        return sobj;
    }
    // �����̸��ٲٱ�
    public DOBJ CALLrouter_file_upload_FUT35(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT35");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT35");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dobj.getRetObject("SEL33").getRecord().get("DFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.rename(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILENAME").toString());
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT35") ;
        dvobj.Println("FUT35");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε����
    public DOBJ CALLrouter_file_upload_UPD36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD36");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD36");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_file_upload_UPD36(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD36") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_file_upload_UPD36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   FILE_NM = dvobj.getRecord().get("FILE_NM");   //���� ��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   SERIAL_NO_SEQ = dvobj.getRecord().get("SERIAL_NO_SEQ");   //�ø������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   SVR_FILE_NM = dobj.getRetObject("SEL33").getRecord().get("DFILENAME");   //���� ���� ��
        String   SVR_FILE_ROUT = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");   //���� ���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=SYSDATE , SVR_FILE_ROUT=:SVR_FILE_ROUT , SVR_FILE_NM=:SVR_FILE_NM , REMAK=:REMAK , FILE_NM=:FILE_NM  \n";
        query +=" where SERIAL_NO=:SERIAL_NO  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SERIAL_NO_SEQ=:SERIAL_NO_SEQ";
        sobj.setSql(query);
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("SVR_FILE_NM", SVR_FILE_NM);               //���� ���� ��
        sobj.setString("SVR_FILE_ROUT", SVR_FILE_ROUT);               //���� ���� ���
        return sobj;
    }
    //##**$$router_file_upload
    //##**$$router_dsct_upd
    /*
    */
    public DOBJ CTLrouter_dsct_upd(DOBJ dobj)
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
            dobj  = CALLrouter_dsct_upd_MIUD2(Conn, dobj);           //  MIUD
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
    public DOBJ CTLrouter_dsct_upd( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrouter_dsct_upd_MIUD2(Conn, dobj);           //  MIUD
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // MIUD
    // �б�
    public DOBJ CALLrouter_dsct_upd_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrouter_dsct_upd_UPD1(Conn, dobj);           //  ����� ���� ������Ʈ
                dobj  = CALLrouter_dsct_upd_UPD6(Conn, dobj);           //  UPD
            }
        }
        return dobj;
    }
    // ����� ���� ������Ʈ
    // ����� ���� ������Ʈ
    public DOBJ CALLrouter_dsct_upd_UPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD1");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_dsct_upd_UPD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrouter_dsct_upd_UPD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   SERIAL_NO_SEQ = dvobj.getRecord().get("SERIAL_NO_SEQ");   //�ø������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   DSCT_START = dvobj.getRecord().get("DSCT_START");   //���ν��ۿ�
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   DSCT_YN = dvobj.getRecord().get("DSCT_YN");   //�������뿩��
        String   DSCT_END = dvobj.getRecord().get("DSCT_END");   //���������
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_OFF_ROUTER_MNG  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , DSCT_END=:DSCT_END , DSCT_YN=:DSCT_YN , DSCT_START=:DSCT_START , MOD_DATE=SYSDATE  \n";
        query +=" where SERIAL_NO=:SERIAL_NO  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SERIAL_NO_SEQ=:SERIAL_NO_SEQ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO_SEQ", SERIAL_NO_SEQ);               //�ø������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("DSCT_START", DSCT_START);               //���ν��ۿ�
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("DSCT_YN", DSCT_YN);               //�������뿩��
        sobj.setString("DSCT_END", DSCT_END);               //���������
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // UPD
    // ���� ����
    public DOBJ CALLrouter_dsct_upd_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrouter_dsct_upd_UPD6(dobj, dvobj);
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
    private SQLObject SQLrouter_dsct_upd_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   DSCT_START = dvobj.getRecord().get("DSCT_START");   //���ν��ۿ�
        String   DSCT_YN = dvobj.getRecord().get("DSCT_YN");   //�������뿩��
        String   DSCT_END = dvobj.getRecord().get("DSCT_END");   //���������
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update LOG.KDS_SHOP  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , DSCT_END=:DSCT_END , DSCT_YN=:DSCT_YN , DSCT_START=:DSCT_START , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("DSCT_START", DSCT_START);               //���ν��ۿ�
        sobj.setString("DSCT_YN", DSCT_YN);               //�������뿩��
        sobj.setString("DSCT_END", DSCT_END);               //���������
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    //##**$$router_dsct_upd
    //##**$$end
}
