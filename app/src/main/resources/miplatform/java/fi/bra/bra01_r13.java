
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_r13
{
    public bra01_r13()
    {
    }
    //##**$$btrip_list
    /*
    */
    public DOBJ CTLbtrip_list(DOBJ dobj)
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
            dobj  = CALLbtrip_list_SEL3(Conn, dobj);           //  �����ڵ� ȹ��
            dobj  = CALLbtrip_list_SEL1(Conn, dobj);           //  �����ȸ
            dobj  = CALLbtrip_list_SEL4(Conn, dobj);           //  ��Ī�ȵ� ��ǥ���
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
    public DOBJ CTLbtrip_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbtrip_list_SEL3(Conn, dobj);           //  �����ڵ� ȹ��
        dobj  = CALLbtrip_list_SEL1(Conn, dobj);           //  �����ȸ
        dobj  = CALLbtrip_list_SEL4(Conn, dobj);           //  ��Ī�ȵ� ��ǥ���
        return dobj;
    }
    // �����ڵ� ȹ��
    public DOBJ CALLbtrip_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbtrip_list_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEPT_CD  ,  A.DEPT_NM,  A.GIBU  FROM  INSA.TCPM_DEPT  A  WHERE  1=1   \n";
        query +=" AND  A.GIBU  <>  'AL'   \n";
        query +=" AND  A.GIBU  =  :BRAN_CD   \n";
        query +=" AND  A.USE_YN  =  'Y' ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ȸ
    public DOBJ CALLbtrip_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbtrip_list_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //����
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  TO_CHAR(INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  BTRIP_DAY  ,  STAFF_CD  ,  INSA.F_GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM  ,  GBN  ,  FIDU.GET_CODE_NM('00415',  GBN)  AS  GBN_NM  ,  TOTAL_FEE  ,  '  '||BTRIP_STAFF_NM  AS  BTRIP_STAFF_NM  ,  BTRIP_PROVCITY_NM  ,  SUBSTR(LPAD(BTRIP_USE_TIME,6,'0'),1,2)  ||  ':'  ||  SUBSTR(LPAD(BTRIP_USE_TIME,6,'0'),3,2)  ||  ':'  ||  SUBSTR(LPAD(BTRIP_USE_TIME,6,'0'),5,2)  AS  BTRIP_USE_TIME  ,  BTRIP_USE_KILO  ,  CHIT_CD  ,  CHIT_SEQ  ,  decode((   \n";
        query +=" SELECT  B.CHIT_CD  ||  B.CHIT_SEQ  FROM  ACCT.TBIL_SLIP  A,  ACCT.TBIL_SLIP_D  B,  ACCT.TBIL_SLIP_REM  C  WHERE  A.CHIT_CD  =  B.CHIT_CD   \n";
        query +=" AND  B.CHIT_CD  =  C.CHIT_CD   \n";
        query +=" AND  B.CHIT_SEQ  =  C.CHIT_SEQ   \n";
        query +=" AND  A.USE_YN  =  'Y'   \n";
        query +=" AND  B.USE_YN  =  'Y'   \n";
        query +=" AND  B.CHIT_CD  =  X.CHIT_CD   \n";
        query +=" AND  B.CHIT_SEQ  =  X.CHIT_SEQ   \n";
        query +=" AND  LENGTH(C.MNG_ITEM_CD)  =  7  )  ,  null,  '��ǥ����',  '',  '��ǥ����',  ''  )  AS  CHIT_GBN  FROM  GIBU.TBRA_BTRIP  X  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  BTRIP_DAY  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  GBN  =  DECODE(:GBN,  '',  GBN,  :GBN)   \n";
        query +=" AND  STAFF_CD  =  DECODE(:STAFF_CD,  '',  STAFF_CD,  :STAFF_CD) ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("GBN", GBN);               //����
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // ��Ī�ȵ� ��ǥ���
    public DOBJ CALLbtrip_list_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbtrip_list_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_list_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEPT_CD = dobj.getRetObject("SEL3").getRecord().get("DEPT_CD");   //�μ� �ڵ�
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.CHIT_CD,  B.CHIT_SEQ,  '  '||B.SYNOP  SYNOP,  B.DR_AMT,  C.MNG_ITEM_CD  FROM  ACCT.TBIL_SLIP  A,  ACCT.TBIL_SLIP_D  B,  ACCT.TBIL_SLIP_REM  C  WHERE  A.CHIT_CD  =  B.CHIT_CD   \n";
        query +=" AND  B.CHIT_CD  =  C.CHIT_CD   \n";
        query +=" AND  B.CHIT_SEQ  =  C.CHIT_SEQ   \n";
        query +=" AND  A.USE_YN  =  'Y'   \n";
        query +=" AND  B.USE_YN  =  'Y'   \n";
        query +=" AND  (B.ACCT_CD  =  '0460'   \n";
        query +=" OR  B.ACCT_CD  =  '0464')   \n";
        query +=" AND  A.DEPT_CD  =  :DEPT_CD   \n";
        query +=" AND  A.CHIT_DAY  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  LENGTH(C.MNG_ITEM_CD)  =  7   \n";
        query +=" AND  C.MNG_ITEM_CD  =  DECODE(:STAFF_CD,  '',  C.MNG_ITEM_CD,  :STAFF_CD)  minus   \n";
        query +=" SELECT  Y.CHIT_CD  ,  Y.CHIT_SEQ  ,  Y.SYNOP  ,  Y.DR_AMT  ,  Y.MNG_ITEM_CD  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  STAFF_CD  ,  CHIT_CD  ,  CHIT_SEQ  FROM  GIBU.TBRA_BTRIP  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  BTRIP_DAY  BETWEEN  SUBSTR(:START_YRMN,  1,  4)  ||  '01'   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  STAFF_CD  =  DECODE(:STAFF_CD,  '',  STAFF_CD,  :STAFF_CD)  )X,   \n";
        query +=" (SELECT  B.CHIT_CD,  B.CHIT_SEQ,  A.CHIT_DAY,  '  '||B.SYNOP  SYNOP,  C.MNG_ITEM_CD,  B.DR_AMT  FROM  ACCT.TBIL_SLIP  A,  ACCT.TBIL_SLIP_D  B,  ACCT.TBIL_SLIP_REM  C  WHERE  A.CHIT_CD  =  B.CHIT_CD   \n";
        query +=" AND  B.CHIT_CD  =  C.CHIT_CD   \n";
        query +=" AND  B.CHIT_SEQ  =  C.CHIT_SEQ   \n";
        query +=" AND  A.USE_YN  =  'Y'   \n";
        query +=" AND  B.USE_YN  =  'Y'   \n";
        query +=" AND  (B.ACCT_CD  =  '0460'   \n";
        query +=" OR  B.ACCT_CD  =  '0464')   \n";
        query +=" AND  A.DEPT_CD  =  :DEPT_CD   \n";
        query +=" AND  A.CHIT_DAY  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  LENGTH(C.MNG_ITEM_CD)  =  7   \n";
        query +=" AND  C.MNG_ITEM_CD  =  DECODE(:STAFF_CD,  '',  C.MNG_ITEM_CD,  :STAFF_CD))  Y  WHERE  X.CHIT_CD  =  Y.CHIT_CD   \n";
        query +=" AND  X.CHIT_SEQ  =  Y.CHIT_SEQ   \n";
        query +=" AND  X.STAFF_CD  =  Y.MNG_ITEM_CD ";
        sobj.setSql(query);
        sobj.setString("DEPT_CD", DEPT_CD);               //�μ� �ڵ�
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$btrip_list
    //##**$$btrip_detail
    /*
    */
    public DOBJ CTLbtrip_detail(DOBJ dobj)
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
            dobj  = CALLbtrip_detail_SEL1(Conn, dobj);           //  ����ȸ
            if(!dobj.getRetObject("SEL1").getRecord().get("FILE_TEMPNM").equals(""))
            {
                dobj  = CALLbtrip_detail_SEL2(Conn, dobj);           //  ÷������ ��ȸ
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
    public DOBJ CTLbtrip_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbtrip_detail_SEL1(Conn, dobj);           //  ����ȸ
        if(!dobj.getRetObject("SEL1").getRecord().get("FILE_TEMPNM").equals(""))
        {
            dobj  = CALLbtrip_detail_SEL2(Conn, dobj);           //  ÷������ ��ȸ
        }
        return dobj;
    }
    // ����ȸ
    public DOBJ CALLbtrip_detail_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbtrip_detail_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_detail_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //����
        String   BTRIP_DAY = dobj.getRetObject("S").getRecord().get("BTRIP_DAY");   //���� ����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,  A.BTRIP_DAY  ,  A.STAFF_CD  ,  A.GBN  ,  A.TOTAL_FEE  ,  A.BTRIP_PROVCITY_NM  ,  A.BTRIP_STAFF_NM  ,  LPAD(A.BTRIP_START_TIME,6,'0')  BTRIP_START_TIME  ,  LPAD(A.BTRIP_USE_TIME,6,'0')  BTRIP_USE_TIME  ,  A.BTRIP_USE_KILO  ,  A.CHIT_CD  ,  A.CHIT_SEQ  ,  A.BIGO  ,  A.INSPRES_ID  ,  TO_CHAR(A.INS_DATE,'YYYYMMDD')  AS  INS_DATE  ,  B.FILE_TEMPNM  ,  B.FILE_ROUT  FROM  GIBU.TBRA_BTRIP  A  ,  GIBU.TBRA_BTRIP_DOC_ATTCH  B  WHERE  A.BRAN_CD  =  B.BRAN_CD(+)   \n";
        query +=" AND  A.BTRIP_DAY  =  B.BTRIP_DAY(+)   \n";
        query +=" AND  A.  STAFF_CD  =  B.STAFF_CD(+)   \n";
        query +=" AND  A.  GBN  =  B.GBN(+)   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.BTRIP_DAY  =  :BTRIP_DAY   \n";
        query +=" AND  A.STAFF_CD  =:STAFF_CD   \n";
        query +=" AND  A.GBN  =  :GBN ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("GBN", GBN);               //����
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //���� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ÷������ ��ȸ
    public DOBJ CALLbtrip_detail_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbtrip_detail_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        String fullname="";
        rvobj.first();
        while(rvobj.next())
        {
            wutil.makeFileOverwirte( dobj.getRetObject("SEL1").getRecord().get("FILE_ROUT"), dobj.getRetObject("SEL1").getRecord().get("FILE_TEMPNM"),rvobj.getRecord().getBytes("FILES"));
        }
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_detail_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //����
        String   BTRIP_DAY = dobj.getRetObject("S").getRecord().get("BTRIP_DAY");   //���� ����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,  A.BTRIP_DAY  ,  A.STAFF_CD  ,  A.GBN  ,  A.FILE_NM  ,  A.FILE_ROUT  ,  A.FILE_TYPE  ,  A.FILE_TEMPNM  ,  A.FILE_SIZE  ,  A.FILES  FROM  GIBU.TBRA_BTRIP_DOC_ATTCH  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.BTRIP_DAY  =  :BTRIP_DAY   \n";
        query +=" AND  A.STAFF_CD  =:STAFF_CD   \n";
        query +=" AND  A.GBN  =  :GBN ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("GBN", GBN);               //����
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //���� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$btrip_detail
    //##**$$btrip_file_save
    /*
    */
    public DOBJ CTLbtrip_file_save(DOBJ dobj)
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
            dobj  = CALLbtrip_file_save_DEL21(Conn, dobj);           //  ���� ÷������ ����
            dobj  = CALLbtrip_file_save_FUT11( dobj);        //  ���Ϲ���
            dobj  = CALLbtrip_file_save_CVT12( dobj);        //  ���Ϲ����̵�����
            dobj  = CALLbtrip_file_save_INS19(Conn, dobj);           //  ���Ͼ��ε���������
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
    public DOBJ CTLbtrip_file_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbtrip_file_save_DEL21(Conn, dobj);           //  ���� ÷������ ����
        dobj  = CALLbtrip_file_save_FUT11( dobj);        //  ���Ϲ���
        dobj  = CALLbtrip_file_save_CVT12( dobj);        //  ���Ϲ����̵�����
        dobj  = CALLbtrip_file_save_INS19(Conn, dobj);           //  ���Ͼ��ε���������
        return dobj;
    }
    // ���� ÷������ ����
    public DOBJ CALLbtrip_file_save_DEL21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL21");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("DEL21");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbtrip_file_save_DEL21(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL21") ;
        rvobj.Println("DEL21");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_file_save_DEL21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   BTRIP_DAY = dvobj.getRecord().get("BTRIP_DAY");   //���� ����
        String   GBN = dvobj.getRecord().get("GBN");   //����
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BTRIP_DOC_ATTCH  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and GBN=:GBN  \n";
        query +=" and BTRIP_DAY=:BTRIP_DAY  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //���� ����
        sobj.setString("GBN", GBN);               //����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        return sobj;
    }
    // ���Ϲ���
    public DOBJ CALLbtrip_file_save_FUT11(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT11");
        VOBJ dvobj = dobj.getRetObject("S1");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT11");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT11") ;
        dvobj.setRetcode(1);
        dvobj.Println("FUT11");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ����̵�����
    public DOBJ CALLbtrip_file_save_CVT12(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT12");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S1");        //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("CVT12");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT12") ;
        dvobj.setRetcode(1);
        dvobj.Println("CVT12");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLbtrip_file_save_INS19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS19");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binFILES=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("S1").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("FILES", binFILES);
        }
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbtrip_file_save_INS19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS19") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_file_save_INS19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FILE_TYPE = dvobj.getRecord().get("FILE_TYPE");   //���� Ÿ��
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //������ȣ
        String   BTRIP_DAY = dobj.getRetObject("S").getRecord().get("BTRIP_DAY");   //���� �ڵ�
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //����
        String   FILE_NM = dobj.getRetObject("S1").getRecord().get("UPFILENAME");   //���ϸ�
        String   FILE_ROUT = dobj.getRetObject("CVT12").getRecord().get("DFILEPATH");   //���ϰ��
        double   FILE_SIZE = dobj.getRetObject("S1").getRecord().getDouble("FILESIZE");   //���� ũ��
        String   FILE_TEMPNM = dobj.getRetObject("S1").getRecord().get("UNIFILENAME");   //��ȯ ���ϸ�
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //����
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BTRIP_DOC_ATTCH (FILE_SIZE, STAFF_CD, GBN, FILE_TEMPNM, FILE_ROUT, FILE_TYPE, BTRIP_DAY, BRAN_CD, FILE_NM, FILES)  \n";
        query +=" values(:FILE_SIZE , :STAFF_CD , :GBN , :FILE_TEMPNM , :FILE_ROUT , :FILE_TYPE , :BTRIP_DAY , :BRAN_CD , :FILE_NM , :FILES )";
        sobj.setSql(query);
        sobj.setString("FILE_TYPE", FILE_TYPE);               //���� Ÿ��
        sobj.setString("BRAN_CD", BRAN_CD);               //������ȣ
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //���� �ڵ�
        sobj.setBlob("FILES", FILES);               //����
        sobj.setString("FILE_NM", FILE_NM);               //���ϸ�
        sobj.setString("FILE_ROUT", FILE_ROUT);               //���ϰ��
        sobj.setDouble("FILE_SIZE", FILE_SIZE);               //���� ũ��
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //��ȯ ���ϸ�
        sobj.setString("GBN", GBN);               //����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        return sobj;
    }
    //##**$$btrip_file_save
    //##**$$btrip_save
    /*
    */
    public DOBJ CTLbtrip_save(DOBJ dobj)
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
            dobj  = CALLbtrip_save_MIUD1(Conn, dobj);           //  ��û������
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
    public DOBJ CTLbtrip_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbtrip_save_MIUD1(Conn, dobj);           //  ��û������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ��û������
    public DOBJ CALLbtrip_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLbtrip_save_INS5(Conn, dobj);           //  �űԵ��
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbtrip_save_UPD6(Conn, dobj);           //  ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbtrip_save_DEL11(Conn, dobj);           //  ����
                dobj  = CALLbtrip_save_DEL12(Conn, dobj);           //  ÷�����ϻ���
            }
        }
        return dobj;
    }
    // ����
    public DOBJ CALLbtrip_save_DEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbtrip_save_DEL11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_save_DEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   BTRIP_DAY = dvobj.getRecord().get("BTRIP_DAY");   //���� ����
        String   GBN = dvobj.getRecord().get("GBN");   //����
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BTRIP  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and GBN=:GBN  \n";
        query +=" and BTRIP_DAY=:BTRIP_DAY  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //���� ����
        sobj.setString("GBN", GBN);               //����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        return sobj;
    }
    // ����
    public DOBJ CALLbtrip_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD6");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbtrip_save_UPD6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        rvobj.Println("UPD6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //��� �Ͻ�
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   CHIT_CD = dvobj.getRecord().get("CHIT_CD");   //��ǥ �ڵ�(��ǥ����(8) + ����(4))
        String   BTRIP_USE_KILO = dvobj.getRecord().get("BTRIP_USE_KILO");   //�� ����Ÿ�
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   BTRIP_USE_TIME = dvobj.getRecord().get("BTRIP_USE_TIME");   //�� �ҿ�ð�
        String   BTRIP_PROVCITY_NM = dvobj.getRecord().get("BTRIP_PROVCITY_NM");   //���� ���� ��
        String   BTRIP_DAY = dvobj.getRecord().get("BTRIP_DAY");   //���� ����
        String   TOTAL_FEE = dvobj.getRecord().get("TOTAL_FEE");   //��ü �δ��
        String   BTRIP_STAFF_NM = dvobj.getRecord().get("BTRIP_STAFF_NM");   //������
        String   BIGO = dvobj.getRecord().get("BIGO");   //������
        String   BTRIP_START_TIME = dvobj.getRecord().get("BTRIP_START_TIME");   //���� ���۽ð�
        String   GBN = dvobj.getRecord().get("GBN");   //����
        String   CHIT_SEQ = dvobj.getRecord().get("CHIT_SEQ");   //��ǥ ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BTRIP  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , CHIT_SEQ=:CHIT_SEQ , BTRIP_START_TIME=:BTRIP_START_TIME , BIGO=:BIGO , BTRIP_STAFF_NM=:BTRIP_STAFF_NM , TOTAL_FEE=:TOTAL_FEE , BTRIP_PROVCITY_NM=:BTRIP_PROVCITY_NM , BTRIP_USE_TIME=:BTRIP_USE_TIME , BTRIP_USE_KILO=:BTRIP_USE_KILO , CHIT_CD=:CHIT_CD , MOD_DATE=SYSDATE  \n";
        query +=" where GBN=:GBN  \n";
        query +=" and BTRIP_DAY=:BTRIP_DAY  \n";
        query +=" and STAFF_CD=:STAFF_CD  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("CHIT_CD", CHIT_CD);               //��ǥ �ڵ�(��ǥ����(8) + ����(4))
        sobj.setString("BTRIP_USE_KILO", BTRIP_USE_KILO);               //�� ����Ÿ�
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("BTRIP_USE_TIME", BTRIP_USE_TIME);               //�� �ҿ�ð�
        sobj.setString("BTRIP_PROVCITY_NM", BTRIP_PROVCITY_NM);               //���� ���� ��
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //���� ����
        sobj.setString("TOTAL_FEE", TOTAL_FEE);               //��ü �δ��
        sobj.setString("BTRIP_STAFF_NM", BTRIP_STAFF_NM);               //������
        sobj.setString("BIGO", BIGO);               //������
        sobj.setString("BTRIP_START_TIME", BTRIP_START_TIME);               //���� ���۽ð�
        sobj.setString("GBN", GBN);               //����
        sobj.setString("CHIT_SEQ", CHIT_SEQ);               //��ǥ ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // �űԵ��
    public DOBJ CALLbtrip_save_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbtrip_save_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   CHIT_CD = dvobj.getRecord().get("CHIT_CD");   //��ǥ �ڵ�(��ǥ����(8) + ����(4))
        String   BTRIP_USE_KILO = dvobj.getRecord().get("BTRIP_USE_KILO");   //�� ����Ÿ�
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   BTRIP_USE_TIME = dvobj.getRecord().get("BTRIP_USE_TIME");   //�� �ҿ�ð�
        String   BTRIP_PROVCITY_NM = dvobj.getRecord().get("BTRIP_PROVCITY_NM");   //���� ���� ��
        String   BTRIP_DAY = dvobj.getRecord().get("BTRIP_DAY");   //���� ����
        String   TOTAL_FEE = dvobj.getRecord().get("TOTAL_FEE");   //��ü �δ��
        String   BTRIP_STAFF_NM = dvobj.getRecord().get("BTRIP_STAFF_NM");   //������
        String   BIGO = dvobj.getRecord().get("BIGO");   //������
        String   BTRIP_START_TIME = dvobj.getRecord().get("BTRIP_START_TIME");   //���� ���۽ð�
        String   GBN = dvobj.getRecord().get("GBN");   //����
        String   CHIT_SEQ = dvobj.getRecord().get("CHIT_SEQ");   //��ǥ ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BTRIP (CHIT_SEQ, INSPRES_ID, GBN, BTRIP_START_TIME, BIGO, BTRIP_STAFF_NM, TOTAL_FEE, BTRIP_DAY, BTRIP_PROVCITY_NM, INS_DATE, BTRIP_USE_TIME, STAFF_CD, BTRIP_USE_KILO, CHIT_CD, BRAN_CD)  \n";
        query +=" values(:CHIT_SEQ , :INSPRES_ID , :GBN , :BTRIP_START_TIME , :BIGO , :BTRIP_STAFF_NM , :TOTAL_FEE , :BTRIP_DAY , :BTRIP_PROVCITY_NM , SYSDATE, :BTRIP_USE_TIME , :STAFF_CD , :BTRIP_USE_KILO , :CHIT_CD , :BRAN_CD )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("CHIT_CD", CHIT_CD);               //��ǥ �ڵ�(��ǥ����(8) + ����(4))
        sobj.setString("BTRIP_USE_KILO", BTRIP_USE_KILO);               //�� ����Ÿ�
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("BTRIP_USE_TIME", BTRIP_USE_TIME);               //�� �ҿ�ð�
        sobj.setString("BTRIP_PROVCITY_NM", BTRIP_PROVCITY_NM);               //���� ���� ��
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //���� ����
        sobj.setString("TOTAL_FEE", TOTAL_FEE);               //��ü �δ��
        sobj.setString("BTRIP_STAFF_NM", BTRIP_STAFF_NM);               //������
        sobj.setString("BIGO", BIGO);               //������
        sobj.setString("BTRIP_START_TIME", BTRIP_START_TIME);               //���� ���۽ð�
        sobj.setString("GBN", GBN);               //����
        sobj.setString("CHIT_SEQ", CHIT_SEQ);               //��ǥ ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ÷�����ϻ���
    public DOBJ CALLbtrip_save_DEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbtrip_save_DEL12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_save_DEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   BTRIP_DAY = dvobj.getRecord().get("BTRIP_DAY");   //���� ����
        String   GBN = dvobj.getRecord().get("GBN");   //����
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BTRIP_DOC_ATTCH  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and GBN=:GBN  \n";
        query +=" and BTRIP_DAY=:BTRIP_DAY  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //���� ����
        sobj.setString("GBN", GBN);               //����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        return sobj;
    }
    //##**$$btrip_save
    //##**$$end
}
