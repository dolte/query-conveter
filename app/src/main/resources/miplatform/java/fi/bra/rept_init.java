
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class rept_init
{
    public rept_init()
    {
    }
    //##**$$rept_init
    /* * ���α׷��� : rept_init
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/24
    * ����    : �ű� �Է� �� �ʱ�ȭ ���� (��������, ��������, û������, û���̷�����) ���� ��ȸ�Ѵ�.
    Input
    END_YRMN (������)
    START_YRMN (���۳��)
    UPSO_CD (���� �ڵ�)
    YEAR (�˻��⵵)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLbra04_s01(DOBJ dobj)
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
            dobj  = CALLrept_init_SEL1(Conn, dobj);           //  �ʱ�ȭ������ȸ
            dobj  = CALLrept_init_SEL2(Conn, dobj);           //  ������ȸ
            dobj  = CALLrept_init_SEL3(Conn, dobj);           //  û�� ��� ���
            dobj  = CALLrept_init_SEL4(Conn, dobj);           //  û�� �̷���ȸ
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
    public DOBJ CTLbra04_s01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_init_SEL1(Conn, dobj);           //  �ʱ�ȭ������ȸ
        dobj  = CALLrept_init_SEL2(Conn, dobj);           //  ������ȸ
        dobj  = CALLrept_init_SEL3(Conn, dobj);           //  û�� ��� ���
        dobj  = CALLrept_init_SEL4(Conn, dobj);           //  û�� �̷���ȸ
        return dobj;
    }
    // �ʱ�ȭ������ȸ
    public DOBJ CALLrept_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_init_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.MONPRNCFEE  ,  B.BSTYP_CD  ,  B.UPSO_GRAD  ,  B.GRAD  ,  C.GRADNM  ,  A.MNGEMSTR_NM  ,  A.CLSBS_YRMN  ,  A.OPBI_DAY  ,  A.STAFF_CD  ,  E.HAN_NM  STAFF_NM  ,  A.UPSO_CD,  A.UPSO_NM  ,  GIBU.FT_GET_LAST_REPT_YRMN(:UPSO_CD,  8)  LAST_YRMN  ,  GIBU.FT_GET_START_REPT_YRMN(:UPSO_CD,  8)  START_YRMN  ,  NVL(D.BALANCE,  0)  BALANCE  ,  NVL(F.ACCU_CNT,  0)  ACCU_CNT  ,  F.JUDG_CD  ,  A.BRAN_CD  ,  G.CLAIM_CNT  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  GRAD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  TRIM(BSTYP_CD)  ||  UPSO_GRAD  GRAD  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  CHG_DAY  DESC,  CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  ,  (   \n";
        query +=" SELECT  :UPSO_CD  UPSO_CD  ,  NVL(SUM(BALANCE),  0)  BALANCE  FROM  (   \n";
        query +=" SELECT  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  WHERE  ROWNUM  =  1  )  D  ,  INSA.TINS_MST01  E  ,  (   \n";
        query +=" SELECT  DECODE(COMPN_DAY,  NULL,  1,  DECODE(JUDG_CD,  2,  1,  4,  1,  0))  ACCU_CNT  ,  JUDG_CD  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  COMPN_DAY  ,  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  ORDER  BY  ACCU_DAY  DESC  )  WHERE  ROWNUM  =  1  )  F  ,  (   \n";
        query +=" SELECT  COUNT(UPSO_CD)  CLAIM_CNT  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  G  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  C.GRAD_GBN  =  B.UPSO_GRAD   \n";
        query +=" AND  D.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  F.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  E.STAFF_NUM(+)  =  A.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������ȸ
    // ��ȸ�⵵�� �������� 3���⵵ �Ա� ������ ��ȸ�Ѵ�.  1. TENV_CODE ���� �Աݱ����� ���� �����´�. �̶� �Աݱ����� HIGH_CD ���� 00147 �̴�. SQL ���� HIGH_CD  ���� CODE_CD��� �÷������� �����ؼ� ���� �����Ѵ�
    public DOBJ CALLrept_init_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_init_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_init_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
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
        query +=" AND  '19'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:BB_YEAR_1)  -  2  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:BB_YEAR_1)  -  2  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:BB_YEAR_1)  -  2  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  AA  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  'ä��',  '22',  DECODE(ACCU_SOLCNT,  1,  '����ذ�',  '��Һг�'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:B_YEAR_1)  -  1  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:B_YEAR_1)  -  1  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:B_YEAR_1)  -  1  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  BB  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  'ä��',  '22',  DECODE(ACCU_SOLCNT,  1,  '����ذ�',  '��Һг�'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
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
    // û�� ��� ���
    // �����ڵ带 �������� ���� �ֱٿ� û���� û�������� ��ȸ�Ѵ� ��ȸ�� û�� ������ UI ȭ�鿡�� �ű� �Ա����� �Է� �� ���۳��, ������, �ݾ׵��� ä��µ� ���ȴ�. �ڵ���ü�� ?�� OCR���̺� ��ȸ
    public DOBJ CALLrept_init_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_init_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_init_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  UPSO_CD  ,  DEMD_YRMN  ,  CLSBS_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  5)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  6)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_ADDT_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  TOT_EADDT_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  10)  DSCT_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  11)  TOT_DEMD_AMT  ,  (   \n";
        query +=" SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00141'   \n";
        query +=" AND  CODE_CD  =  GIBU.FT_SPLIT(DEMDS,  ',',  5)  )  DEMD_GBN_NM  FROM  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  XA.CLSBS_YRMN  ,  XB.END_YRMN  DEMD_YRMN  ,  GIBU.FT_GET_DEMD_AMT(XA.UPSO_CD,  XB.START_YRMN,  XB.END_YRMN,  XC.DEMD_YRMN,  'O')  DEMDS  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  GIBU.FT_GET_START_REPT_YRMN(:UPSO_CD,  6)  START_YRMN  ,  TO_CHAR(SYSDATE,  'YYYYMM')  END_YRMN  FROM  DUAL  )  XB  ,  (   \n";
        query +=" SELECT  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  )  XC  WHERE  XA.UPSO_CD  =  :UPSO_CD  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // û�� �̷���ȸ
    // �Ա��� �Ϸ���� ���� û���̷������� ��ȸ�Ѵ�
    public DOBJ CALLrept_init_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_init_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_init_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.UPSO_CD  ,  XB.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  XC.CODE_NM  DEMD_GBN_NM  ,  XB.MONPRNCFEE  ,  XB.TOT_USE_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_EADDT_AMT  ,  XB.TOT_DEMD_AMT  ,  XB.DEMD_GBN  ,  XB.START_YRMN  ||  '01'  START_YRMN  ,  XB.END_YRMN  ||  '01'  END_YRMN  FROM  (   \n";
        query +=" SELECT  NVL(MAX(NOTE_YRMN),  '190001')  DEMD_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_GBN  IN  ('01','02','03','04','05','22')  )  XA  ,  GIBU.TBRA_DEMD_OCR  XB  ,  FIDU.TENV_CODE  XC  WHERE  XB.DEMD_YRMN  >  XA.DEMD_YRMN   \n";
        query +=" AND  XB.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XC.HIGH_CD  =  '00141'   \n";
        query +=" AND  XC.CODE_CD  =  XB.DEMD_GBN  ORDER  BY  XB.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_init
    //##**$$end
}
