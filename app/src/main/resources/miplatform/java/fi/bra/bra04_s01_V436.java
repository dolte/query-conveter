
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s01
{
    public bra04_s01()
    {
    }
    //##**$$rept_detail_999
    /* * ���α׷��� : bra04_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/23
    * ����    : �ش����� 3��ġ ���������� ��������� ��ȸ�Ѵ�.
    Input
    UPSO_CD (���� �ڵ�)
    YEAR (�˻��⵵)
    * ������ : 2010/02/25
    * ������ :
    * �������� : ��Ұ����� �� ���ڸ� START_YRMN, END_YRMN�� �ƴ϶� SOL_START_YRMN, SOL_END_YRMN���� ����
    */
    public DOBJ CTLrept_detail_99(DOBJ dobj)
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
            dobj  = CALLrept_detail_999_SEL1(Conn, dobj);           //  ������ȸ
            dobj  = CALLrept_detail_999_SEL2(Conn, dobj);           //  ������� ��ȸ
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
    public DOBJ CTLrept_detail_99( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_detail_999_SEL1(Conn, dobj);           //  ������ȸ
        dobj  = CALLrept_detail_999_SEL2(Conn, dobj);           //  ������� ��ȸ
        return dobj;
    }
    // ������ȸ
    // ��ȸ�⵵�� �������� 3���⵵ �Ա� ������ ��ȸ�Ѵ�.  1. TENV_CODE ���� �Աݱ����� ���� �����´�. �̶� �Աݱ����� HIGH_CD ���� 00147 �̴�. SQL ���� HIGH_CD  ���� CODE_CD��� �÷������� �����ؼ� ���� �����Ѵ�
    public DOBJ CALLrept_detail_999_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_detail_999_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_999_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BB_YEAR ="99";        //���۳�
        String B_YEAR ="99";        //�۳�
        String   BB_YEAR_1 = dobj.getRetObject("S").getRecord().get("YEAR");   //���۳�
        String   B_YEAR_1 = dobj.getRetObject("S").getRecord().get("YEAR");   //�۳�
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //�˻��⵵
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  AA.UPSO_CD  BB_UPSO_CD  ,  AA.YRMN  BB_REPT_YRMN  ,  AA.MM  BB_MM  ,  AA.REPT_GBN  BB_REPT_GBN  ,  AA.CODE_NM  BB_CODE_NM  ,  AA.USE_AMT  BB_USE_AMT  ,  AA.REPT_DAY  BB_REPT_DAY  ,  AA.REPT_NUM  BB_REPT_NUM  ,  AA.RECV_DAY  BB_RECV_DAY  ,  AA.ACCU_GBN  BB_ACCU_GBN  ,  AA.DISTR_SEQ  BB_DISTR_SEQ  ,  AA.MAPPING_DAY  BB_MAPPING_DAY  ,  BB.UPSO_CD  B_UPSO_CD  ,  BB.YRMN  B_REPT_YRMN  ,  BB.MM  B_MM  ,  BB.REPT_GBN  B_REPT_GBN  ,  BB.CODE_NM  B_CODE_NM  ,  BB.USE_AMT  B_USE_AMT  ,  BB.REPT_DAY  B_REPT_DAY  ,  BB.REPT_NUM  B_REPT_NUM  ,  BB.RECV_DAY  B_RECV_DAY  ,  BB.ACCU_GBN  B_ACCU_GBN  ,  BB.DISTR_SEQ  B_DISTR_SEQ  ,  BB.MAPPING_DAY  B_MAPPING_DAY  ,  CC.UPSO_CD  ,  CC.YRMN  REPT_YRMN  ,  CC.MM  ,  CC.REPT_GBN  ,  CC.CODE_NM  ,  CC.USE_AMT  ,  CC.REPT_DAY  ,  CC.REPT_NUM  ,  CC.RECV_DAY  ,  CC.ACCU_GBN  ,  CC.DISTR_SEQ  ,  CC.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  'ä��',  '22',  DECODE(ACCU_SOLCNT,  1,  '����ذ�',  '��Һг�'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  WHEN  REPT_GBN  =  '04'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN   \n";
        query +=" AND  JUDG_CD  NOT  IN  ('2',  '3',  '4',  '8')  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:BB_YEAR_1)  -  2  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:BB_YEAR_1)  -  2  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:BB_YEAR_1)  -  2  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  AA  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  'ä��',  '22',  DECODE(ACCU_SOLCNT,  1,  '����ذ�',  '��Һг�'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  WHEN  REPT_GBN  =  '04'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN   \n";
        query +=" AND  JUDG_CD  NOT  IN  ('2',  '3',  '4',  '8')  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:B_YEAR_1)  -  1  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:B_YEAR_1)  -  1  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:B_YEAR_1)  -  1  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  BB  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  'ä��',  '22',  DECODE(ACCU_SOLCNT,  1,  '����ذ�',  '��Һг�'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  WHEN  REPT_GBN  =  '04'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN   \n";
        query +=" AND  JUDG_CD  NOT  IN  ('2',  '3',  '4',  '8')  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  :YEAR  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  CC  WHERE  BB.MM  =  AA.MM   \n";
        query +=" AND  CC.MM  =  AA.MM  ORDER  BY  AA.MM ";
        sobj.setSql(query);
        sobj.setString("BB_YEAR_1", BB_YEAR_1);               //���۳�
        sobj.setString("B_YEAR_1", B_YEAR_1);               //�۳�
        sobj.setString("YEAR", YEAR);               //�˻��⵵
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������� ��ȸ
    // ������� ��ȸ
    public DOBJ CALLrept_detail_999_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_detail_999_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_999_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  ACCU_GBN  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  JUDG_CD  FROM  (   \n";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  ACCU_GBN  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  JUDG_CD  ,  (   \n";
        query +=" SELECT  COUNT(YRMN)  FROM  GIBU.COPY_YRMN  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  A.SOL_START_YRMN   \n";
        query +=" AND  A.SOL_END_YRMN  )  DEMD_MMCNT  FROM  GIBU.TBRA_ACCU  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  ORDER  BY  ACCU_DAY  DESC,  ACCU_NUM  DESC  )  WHERE  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_detail_999
    //##**$$rept_closing
    /* * ���α׷��� : bra04_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/18
    * ����    : �Աݿ��� �Աݸ��� ������ Ȯ���Ѵ�.
    Input
    BRAN_CD (���� �ڵ�)
    REPT_DAY (�Ա�����)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_closing(DOBJ dobj)
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
            dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  ���κ�����üũ
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
    public DOBJ CTLrept_closing( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  ���κ�����üũ
        return dobj;
    }
    // ���κ�����üũ
    // ���κ� �������̺��� �ش� ��� ������ �ִ��� üũ�Ѵ�
    public DOBJ CALLrept_closing_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_closing_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_closing_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա� ����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(:REPT_DAY,1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(:REPT_DAY,5,2)  =  END_MON   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_closing
    //##**$$indtn_rept_save
    /* * ���α׷��� : bra04_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/25
    * ����    : �Աݳ����� ����/�����Ѵ�. (���� = ���� �� �ű�����)
    1) ������ : ������ �Աݳ����� ���� ������ �����Ѵ�.
    2) �׿� : �Ա������� �����ϰ� ���ҿ� �����Ѵ�.
    3) ��� ������ ��� ��� ó�� (��� �Ϸ� �� û���� �����)
    * û���ݾװ� �Աݱݾ��� ��ġ���� �ʴ� ��� �� ���� �ݾ��� �ܰ� ���̺� (TBRA_REPT_BALANCE) �� ����ȴ�.
    4) �Աݳ��� ������ ������ ���
    �Ա� ���� ������ �ش� ���ҿ� ���ε� �Ա������� ���� ���
    Input
    ACCN_NUM (���� ��ȣ)
    BALANCE (�ܾ�)
    BANK_CD (���� �ڵ�)
    BRAN_CD (���� �ڵ�)
    CLAIM_GBN (ä�� �Աݱ���)
    COMIS (������)
    CRUD (CRUD)
    DEMD_MMCNT (û��������)
    DISTR_GBN (�Ա� �й� ����)
    DISTR_SEQ (�й� ����)
    END_YRMN (������)
    INSPRES_ID (����� ID)
    NOTE_YRMN (���� ���)
    RECV_DAY (���� ����)
    REMAK (���)
    REPT_AMT (�Ա� �ݾ�)
    REPT_DAY (�Ա�����)
    REPT_GBN (�Ա� ����)
    REPT_NUM (�Ա� ��ȣ)
    START_YRMN (���۳��)
    UPSO_CD (���� �ڵ�)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLindtn_rept_save(DOBJ dobj)
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
            dobj  = CALLindtn_rept_save_DEL1(Conn, dobj);           //  ���� ���� ����
            if( dobj.getRetObject("S").getRecord().get("CRUD").equals("I"))
            {
                dobj  = CALLindtn_rept_save_SEL2(Conn, dobj);           //  ����� ����Ȯ��
                if( dobj.getRetObject("S").getRecord().get("CLAIM_GBN").equals("1"))
                {
                    dobj  = CALLindtn_rept_save_OSP1(Conn, dobj);           //  ���� ä�� �Ա� ����
                    dobj  = CALLindtn_rept_save_SEL3(Conn, dobj);           //  �������� ��ȸ
                }
                else if( dobj.getRetObject("SEL2").getRecord().getDouble("ACCU_CNT") == 0)
                {
                    dobj  = CALLindtn_rept_save_OSP2(Conn, dobj);           //  ���� �Ա�
                    dobj  = CALLindtn_rept_save_SEL3(Conn, dobj);           //  �������� ��ȸ
                }
                else
                {
                    dobj  = CALLindtn_rept_save_OSP3(Conn, dobj);           //  ��� ���� �Ա�
                    dobj  = CALLindtn_rept_save_SEL3(Conn, dobj);           //  �������� ��ȸ
                }
            }
            else
            {
                dobj  = CALLindtn_rept_save_SEL8(Conn, dobj);           //  ��� �Ա� Ȯ��
                if( dobj.getRetObject("SEL8").getRecord().get("ACCU_GBN").equals("06"))
                {
                    dobj  = CALLindtn_rept_save_OSP4(Conn, dobj);           //  ���� ä�� �Ա� ����
                    dobj  = CALLindtn_rept_save_SEL3(Conn, dobj);           //  �������� ��ȸ
                }
                else if(!dobj.getRetObject("SEL8").getRecord().get("ACCU_GBN").equals("22"))
                {
                    dobj  = CALLindtn_rept_save_OSP5(Conn, dobj);           //  ���� �Ա� ����
                    dobj  = CALLindtn_rept_save_SEL3(Conn, dobj);           //  �������� ��ȸ
                }
                else
                {
                    dobj  = CALLindtn_rept_save_OSP11(Conn, dobj);           //  ���� ��� �Ա� ����
                    dobj  = CALLindtn_rept_save_SEL3(Conn, dobj);           //  �������� ��ȸ
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
    public DOBJ CTLindtn_rept_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLindtn_rept_save_DEL1(Conn, dobj);           //  ���� ���� ����
        if( dobj.getRetObject("S").getRecord().get("CRUD").equals("I"))
        {
            dobj  = CALLindtn_rept_save_SEL2(Conn, dobj);           //  ����� ����Ȯ��
            if( dobj.getRetObject("S").getRecord().get("CLAIM_GBN").equals("1"))
            {
                dobj  = CALLindtn_rept_save_OSP1(Conn, dobj);           //  ���� ä�� �Ա� ����
                dobj  = CALLindtn_rept_save_SEL3(Conn, dobj);           //  �������� ��ȸ
            }
            else if( dobj.getRetObject("SEL2").getRecord().getDouble("ACCU_CNT") == 0)
            {
                dobj  = CALLindtn_rept_save_OSP2(Conn, dobj);           //  ���� �Ա�
                dobj  = CALLindtn_rept_save_SEL3(Conn, dobj);           //  �������� ��ȸ
            }
            else
            {
                dobj  = CALLindtn_rept_save_OSP3(Conn, dobj);           //  ��� ���� �Ա�
                dobj  = CALLindtn_rept_save_SEL3(Conn, dobj);           //  �������� ��ȸ
            }
        }
        else
        {
            dobj  = CALLindtn_rept_save_SEL8(Conn, dobj);           //  ��� �Ա� Ȯ��
            if( dobj.getRetObject("SEL8").getRecord().get("ACCU_GBN").equals("06"))
            {
                dobj  = CALLindtn_rept_save_OSP4(Conn, dobj);           //  ���� ä�� �Ա� ����
                dobj  = CALLindtn_rept_save_SEL3(Conn, dobj);           //  �������� ��ȸ
            }
            else if(!dobj.getRetObject("SEL8").getRecord().get("ACCU_GBN").equals("22"))
            {
                dobj  = CALLindtn_rept_save_OSP5(Conn, dobj);           //  ���� �Ա� ����
                dobj  = CALLindtn_rept_save_SEL3(Conn, dobj);           //  �������� ��ȸ
            }
            else
            {
                dobj  = CALLindtn_rept_save_OSP11(Conn, dobj);           //  ���� ��� �Ա� ����
                dobj  = CALLindtn_rept_save_SEL3(Conn, dobj);           //  �������� ��ȸ
            }
        }
        return dobj;
    }
    // ���� ���� ����
    public DOBJ CALLindtn_rept_save_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLindtn_rept_save_DEL1(dobj, dvobj);
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
    private SQLObject SQLindtn_rept_save_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_ERR  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ����� ����Ȯ��
    // ������� ��ü���� Ȯ���Ѵ�  ��� : �Ϸ����� ���� ��� ������� : JUDG_CD = 2 ����� : JUDG_CD = 4 �׿� ��� �ƴ�
    public DOBJ CALLindtn_rept_save_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLindtn_rept_save_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtn_rept_save_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(COMPN_DAY,  NULL,  1,  DECODE(JUDG_CD,  2,  1,  4,  1,  0))  ACCU_CNT  ,  JUDG_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  COMPN_DAY  ,  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  ORDER  BY  ACCU_DAY  DESC  )  WHERE  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� ä�� �Ա� ����
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLindtn_rept_save_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP1");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP1");
        robj.setRetcode(1);
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �������� ��ȸ
    public DOBJ CALLindtn_rept_save_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLindtn_rept_save_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtn_rept_save_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //�Ա� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  UPSO_CD  ,  START_YRMN  ,  END_YRMN  ,  REPT_AMT  ,  COMIS  ,  RECV_DAY  ,  ACCN_NUM  ,  ERR_GBN  ,  ERR_CTENT  FROM  GIBU.TBRA_REPT_ERR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLindtn_rept_save_OSP2(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP2");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP2");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP2");
        robj.setRetcode(1);
        robj.Println("OSP2");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ��� ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLindtn_rept_save_OSP3(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP3");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP3");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN_ACCU";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP3");
        robj.setRetcode(1);
        robj.Println("OSP3");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ��� �Ա� Ȯ��
    public DOBJ CALLindtn_rept_save_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLindtn_rept_save_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtn_rept_save_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //�Ա� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� ä�� �Ա� ����
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLindtn_rept_save_OSP4(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP4");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP4");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP4");
        robj.setRetcode(1);
        robj.Println("OSP4");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա� ����
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLindtn_rept_save_OSP5(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP5");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP5");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP5");
        robj.setRetcode(1);
        robj.Println("OSP5");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� ��� �Ա� ����
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLindtn_rept_save_OSP11(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP11");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP11");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN_ACCU";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP11");
        robj.setRetcode(1);
        robj.Println("OSP11");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$indtn_rept_save
    //##**$$rept_demd_amt
    /* * ���α׷��� : bra04_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/04
    * ����    : ���۳��, �������� �������� û���ݾ��� ����Ѵ�.
    - ���۳���� ��� ���� ������ �������� Procedure ���� ���� ó����
    ���/���� ä���Ƿڰ� �ִ� ��� ��� ����, ����ä�� �������� û���ݾ��� ����Ѵ�.
    Input
    ACCU_GBN (��� ����)
    DEMD_GBN (û�� ����)
    END_YRMN (������)
    START_YRMN (���۳��)
    UPSO_CD (���� �ڵ�)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_demd_amt(DOBJ dobj)
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
            dobj  = CALLrept_demd_amt_SEL1(Conn, dobj);           //  û�������� ��ȸ
            dobj  = CALLrept_demd_amt_OSP1(Conn, dobj);           //  û�� ������ ����
            dobj  = CALLrept_demd_amt_SEL3(Conn, dobj);           //  û���ݾ� ��� ����
            dobj  = CALLrept_demd_amt_SEL4(Conn, dobj);           //  �������
            dobj  = CALLrept_demd_amt_SEL6(Conn, dobj);           //  ä�� �Ƿ� ����
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
    public DOBJ CTLrept_demd_amt( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_demd_amt_SEL1(Conn, dobj);           //  û�������� ��ȸ
        dobj  = CALLrept_demd_amt_OSP1(Conn, dobj);           //  û�� ������ ����
        dobj  = CALLrept_demd_amt_SEL3(Conn, dobj);           //  û���ݾ� ��� ����
        dobj  = CALLrept_demd_amt_SEL4(Conn, dobj);           //  �������
        dobj  = CALLrept_demd_amt_SEL6(Conn, dobj);           //  ä�� �Ƿ� ����
        return dobj;
    }
    // û�������� ��ȸ
    // �����ڵ�, ���۳��, �������� �������� û�������͸� ��ȸ�Ѵ�
    public DOBJ CALLrept_demd_amt_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_demd_amt_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_demd_amt_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   DEMD_GBN = dobj.getRetObject("S").getRecord().get("DEMD_GBN");   //û�� ����
        String   RECV_DAY = dobj.getRetObject("S").getRecord().get("RECV_DAY");   //���� ����
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  NVL(:DEMD_GBN,  'O')  AS  DEMD_GBN  ,  SUBSTR(:START_YRMN,  1,  6)  AS  START_YRMN  ,  SUBSTR(:END_YRMN,  1,  6)  AS  END_YRMN  ,  :RECV_DAY  AS  RECV_DAY  FROM  (   \n";
        query +=" SELECT  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("DEMD_GBN", DEMD_GBN);               //û�� ����
        sobj.setString("RECV_DAY", RECV_DAY);               //���� ����
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // û�� ������ ����
    // û�� �����͸� �����ϱ� ���� ���ν��� (GIBU.SP_PROC_DEMDGIRO) �� ȣ���Ѵ�
    public DOBJ CALLrept_demd_amt_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL1");         //û�������� ��ȸ���� ������Ų OBJECT�Դϴ�.(CALLrept_demd_amt_SEL1)
        dvobj.Println("OSP1");
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","DEMD_GBN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_GET_DEMD_AMT";
        int[]    intypes={12, 12, 12, 12, 12};;
        String[] outcolnms={"BSTYP_CD","UPSO_GRAD","MONPRNCFEE","DEMD_GBN","DEMD_MMCNT","TOT_USE_AMT","TOT_ADDT_AMT","TOT_EADDT_AMT","DSCT_AMT","TOT_DEMD_AMT"};;
        int[]    outtypes ={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP1");
        robj.setRetcode(1);
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // û���ݾ� ��� ����
    public DOBJ CALLrept_demd_amt_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_demd_amt_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_demd_amt_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   TOT_ADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        double   TOT_USE_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("TOT_USE_AMT");   //�� ��� �ݾ�
        double   TOT_DEMD_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("TOT_DEMD_AMT");   //�� û�� �ݾ�
        double   DSCT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("DSCT_AMT");   //���� �ݾ� - �ڵ���ü�� ����
        double   DEMD_MMCNT = dobj.getRetObject("OSP1").getRecord().getDouble("DEMD_MMCNT");   //û��������
        double   MONPRNCFEE = dobj.getRetObject("OSP1").getRecord().getDouble("MONPRNCFEE");   //������
        String   DEMD_GBN = dobj.getRetObject("S").getRecord().get("DEMD_GBN");   //û�� ����
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        double   TOT_EADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("TOT_EADDT_AMT");   //�� �߰��� �ݾ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :TOT_USE_AMT  AS  TOT_USE_AMT  ,  :TOT_ADDT_AMT  +  :TOT_EADDT_AMT  AS  TOT_ADDT_AMT  ,  :DSCT_AMT  AS  DSCT_AMT  ,  :MONPRNCFEE  AS  MONPRNCFEE  ,  DECODE(:DEMD_GBN,  'A',  '31',  '32')  AS  REPT_GBN  ,  :DEMD_MMCNT  AS  DEMD_MMCNT  ,  :START_YRMN  AS  START_YRMN  ,  :END_YRMN  AS  END_YRMN  ,  :TOT_DEMD_AMT  AS  TOT_DEMD_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setDouble("TOT_USE_AMT", TOT_USE_AMT);               //�� ��� �ݾ�
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //�� û�� �ݾ�
        sobj.setDouble("DSCT_AMT", DSCT_AMT);               //���� �ݾ� - �ڵ���ü�� ����
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //û��������
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setString("DEMD_GBN", DEMD_GBN);               //û�� ����
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setDouble("TOT_EADDT_AMT", TOT_EADDT_AMT);               //�� �߰��� �ݾ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // �������
    public DOBJ CALLrept_demd_amt_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_demd_amt_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_demd_amt_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  ACCU_GBN  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  JUDG_CD  FROM  (   \n";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  ACCU_GBN  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  JUDG_CD  ,  (   \n";
        query +=" SELECT  COUNT(YRMN)  FROM  GIBU.COPY_YRMN  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  A.SOL_START_YRMN   \n";
        query +=" AND  A.SOL_END_YRMN  )  DEMD_MMCNT  FROM  GIBU.TBRA_ACCU  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  ORDER  BY  ACCU_DAY  DESC,  ACCU_NUM  DESC  )  WHERE  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ä�� �Ƿ� ����
    public DOBJ CALLrept_demd_amt_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_demd_amt_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_demd_amt_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  APPTN_YRMN  ,  STAFF_CD  ,  MONPRNCFEE  ,  DEMD_MMCNT  ,  START_YRMN  ,  END_YRMN  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  ,  TOT_DEMD_AMT  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_demd_amt
    //##**$$get_use_amt
    /* * ���α׷��� : bra04_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/04
    * ����    : �Աݱݾ��� �Է¹޾� ó�������� û�� ���۳��, ������, û������, û���ݾ� ���� ����Ѵ�.
    Input
    REPT_AMT (�Ա� �ݾ�)
    UPSO_CD (���� �ڵ�)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLget_use_amt(DOBJ dobj)
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
            dobj  = CALLget_use_amt_SEL1(Conn, dobj);           //  ���۳�� ���
            dobj  = CALLget_use_amt_OSP1(Conn, dobj);           //  û����� ���
            dobj  = CALLget_use_amt_SEL2(Conn, dobj);           //  û�����,�ݾ� ����
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
    public DOBJ CTLget_use_amt( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_use_amt_SEL1(Conn, dobj);           //  ���۳�� ���
        dobj  = CALLget_use_amt_OSP1(Conn, dobj);           //  û����� ���
        dobj  = CALLget_use_amt_SEL2(Conn, dobj);           //  û�����,�ݾ� ����
        return dobj;
    }
    // ���۳�� ���
    // ���۳�� ���
    public DOBJ CALLget_use_amt_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLget_use_amt_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_use_amt_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MIN(YRMN)  START_YRMN  ,  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  )  B  WHERE  A.YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD  )   \n";
        query +=" AND  A.YRMN  >=  (   \n";
        query +=" SELECT  SUBSTR(OPBI_DAY,  1,  6)  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // û����� ���
    // �Աݱݾ��� �Է¹޾� �Աݱݾ����� ó�������� û������� ����Ѵ�
    public DOBJ CALLget_use_amt_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP1");
        String[]  incolumns ={"UPSO_CD","REPT_AMT","START_YRMN","DEMD_YRMN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   DEMD_YRMN = dobj.getRetObject("SEL1").getRecord().get("DEMD_YRMN");         //û�� ���
            record.put("DEMD_YRMN",DEMD_YRMN);
            ////
            String   START_YRMN = dobj.getRetObject("SEL1").getRecord().get("START_YRMN");         //���۳��
            record.put("START_YRMN",START_YRMN);
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_GET_DEMD_INFO";
        int[]    intypes={12, 12, 12, 12, 12};;
        String[] outcolnms={"END_YRMN","DEMD_AMT","DEMD_MMCNT"};;
        int[]    outtypes ={12, 12, 12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP1");
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // û�����,�ݾ� ����
    // û�����, �ݾ��� �����Ѵ�
    public DOBJ CALLget_use_amt_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLget_use_amt_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_use_amt_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   DEMD_MMCNT = dobj.getRetObject("OSP1").getRecord().getDouble("DEMD_MMCNT");   //û��������
        double   DEMD_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("DEMD_AMT");   //û�� �ݾ�
        String   END_YRMN = dobj.getRetObject("OSP1").getRecord().get("END_YRMN");   //������
        String   START_YRMN = dobj.getRetObject("SEL1").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(:START_YRMN,  NULL,  NULL,  :START_YRMN  ||  '01')  AS  START_YRMN  ,  DECODE(:END_YRMN,  NULL,  NULL,  :END_YRMN  ||  '01')  AS  END_YRMN  ,  DECODE(:DEMD_AMT,  NULL,  0,  :DEMD_AMT)  AS  DEMD_AMT  ,  DECODE(:DEMD_MMCNT,  NULL,  0,  :DEMD_MMCNT)  AS  DEMD_MMCNT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //û��������
        sobj.setDouble("DEMD_AMT", DEMD_AMT);               //û�� �ݾ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$get_use_amt
    //##**$$end
}
