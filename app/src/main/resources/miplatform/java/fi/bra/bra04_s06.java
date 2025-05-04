
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s06
{
    public bra04_s06()
    {
    }
    //##**$$rept_detail
    /* * ���α׷��� : bra04_s06
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/30
    * ����    : 1) ��ȸ����� �������� �ش� ������ 3�Ⱓ�� ���������� ��ȸ�Ѵ�.
    2) ������ �⺻ ������ ��ȸ�Ѵ�.
    3) �Աݱݾ����� ó�������� ���۳��, ������, û������, û���ݾ� ������ ����Ѵ�.
    4) ��������� ��ȸ�Ѵ�.
    Input
    REPT_AMT (�Ա� �ݾ�)
    UPSO_CD (���� �ڵ�)
    YEAR (�˻��⵵)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_detail(DOBJ dobj)
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
            dobj  = CALLrept_detail_SEL1(Conn, dobj);           //  ������ȸ
            dobj  = CALLrept_detail_SEL2(Conn, dobj);           //  ���� ���� ��ȸ
            dobj  = CALLrept_detail_SEL5(Conn, dobj);           //  ���۳�� ���
            dobj  = CALLrept_detail_OSP3(Conn, dobj);           //  û����� ���
            dobj  = CALLrept_detail_SEL3(Conn, dobj);           //  û�����,�ݾ� ����
            dobj  = CALLrept_detail_SEL4(Conn, dobj);           //  ������� ��ȸ
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
    public DOBJ CTLrept_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_detail_SEL1(Conn, dobj);           //  ������ȸ
        dobj  = CALLrept_detail_SEL2(Conn, dobj);           //  ���� ���� ��ȸ
        dobj  = CALLrept_detail_SEL5(Conn, dobj);           //  ���۳�� ���
        dobj  = CALLrept_detail_OSP3(Conn, dobj);           //  û����� ���
        dobj  = CALLrept_detail_SEL3(Conn, dobj);           //  û�����,�ݾ� ����
        dobj  = CALLrept_detail_SEL4(Conn, dobj);           //  ������� ��ȸ
        return dobj;
    }
    // ������ȸ
    // ��ȸ�⵵�� �������� 2���⵵ �Ա� ������ ��ȸ�Ѵ�.  1. TENV_CODE ���� �Աݱ����� ���� �����´�. �̶� �Աݱ����� HIGH_CD ���� 00141 �̴�. SQL ���� HIGH_CD  ���� CODE_CD��� �÷������� �����ؼ� ���� �����Ѵ�
    public DOBJ CALLrept_detail_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_detail_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // ���� ���� ��ȸ
    public DOBJ CALLrept_detail_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_detail_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.MONPRNCFEE  ,  B.MONPRNCFEE2  ,  B.BSTYP_CD  ,  B.UPSO_GRAD  ,  B.GRAD  ,  C.GRADNM  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  UPSO_ADDR1  ,  A.UPSO_REF_INFO  UPSO_ADDR2  ,  A.NEW_DAY  ,  A.OPBI_DAY  ,  A.BIOWN_NUM  ,  A.MNGEMSTR_NM  ,  A.CLSBS_YRMN  ,  A.STAFF_CD  ,  F.HAN_NM  STAFF_NM  ,  A.UPSO_CD,  A.UPSO_NM  ,  GIBU.FT_GET_LAST_REPT_YRMN(:UPSO_CD,  6)  LAST_YRMN  ,  G.DEPT_NM  ,  A.BRAN_CD  ,  (   \n";
        query +=" SELECT  NVL(MAX(BALANCE),  0)  BALANCE  FROM  (   \n";
        query +=" SELECT  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  WHERE  ROWNUM  =  1  )  BALANCE  ,   \n";
        query +=" (SELECT  DECODE(COUNT(*),  0,'N',  'Y')  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL)  CLAIM_YN  ,   \n";
        query +=" (SELECT  NVL(MAX(APPTN_YRMN),'')  APPTN_YRMN  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL)  CLAIM_APPTN_YRMN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  GRAD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  TRIM(BSTYP_CD)  ||  UPSO_GRAD  GRAD  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  CHG_DAY  DESC,  CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  ,  INSA.TINS_MST01  F  ,  INSA.TCPM_DEPT  G  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  C.GRAD_GBN  =  B.UPSO_GRAD   \n";
        query +=" AND  F.STAFF_NUM(+)  =  A.STAFF_CD   \n";
        query +=" AND  A.BRAN_CD  =  G.GIBU ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���۳�� ���
    // ���۳�� ���
    public DOBJ CALLrept_detail_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_detail_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   OPBI_DAY = dobj.getRetObject("SEL2").getRecord().get("OPBI_DAY");   //���� ����
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MIN(YRMN)  START_YRMN  ,  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  )  B  WHERE  A.YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD  )   \n";
        query +=" AND  A.YRMN  >=  SUBSTR(:OPBI_DAY,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("OPBI_DAY", OPBI_DAY);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // û����� ���
    // �Աݱݾ��� �Է¹޾� �Աݱݾ����� ó�������� û������� ����Ѵ�
    public DOBJ CALLrept_detail_OSP3(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP3");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"UPSO_CD","REPT_AMT","START_YRMN","DEMD_YRMN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   DEMD_YRMN = dobj.getRetObject("SEL5").getRecord().get("DEMD_YRMN");         //û�� ���
            record.put("DEMD_YRMN",DEMD_YRMN);
            ////
            String   START_YRMN = dobj.getRetObject("SEL5").getRecord().get("START_YRMN");         //���۳��
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
        robj.setName("OSP3");
        dobj.setRetObject(robj);
        return dobj;
    }
    // û�����,�ݾ� ����
    // û�����, �ݾ��� �����Ѵ�
    public DOBJ CALLrept_detail_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_detail_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   DEMD_MMCNT = dobj.getRetObject("OSP3").getRecord().getDouble("DEMD_MMCNT");   //û��������
        double   DEMD_AMT = dobj.getRetObject("OSP3").getRecord().getDouble("DEMD_AMT");   //û�� �ݾ�
        String   END_YRMN = dobj.getRetObject("OSP3").getRecord().get("END_YRMN");   //������
        String   START_YRMN = dobj.getRetObject("SEL5").getRecord().get("START_YRMN");   //���۳��
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
    // ������� ��ȸ
    // �ش� ������ ��������� ��ȸ�Ѵ�
    public DOBJ CALLrept_detail_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_detail_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  ACCU_GBN  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  JUDG_CD  ,  (   \n";
        query +=" SELECT  COUNT(YRMN)  FROM  GIBU.COPY_YRMN  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  A.SOL_START_YRMN   \n";
        query +=" AND  A.SOL_END_YRMN  )  DEMD_MMCNT  FROM  GIBU.TBRA_ACCU  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_detail
    //##**$$rept_bankbook_insert
    /* * ���α׷��� : bra04_s06
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/04
    * ����    : ������ �Աݳ����� ���� ���� ������ ó���� �� �� ����� �����Ѵ�.
    �Աݱݾ��� û���ݾ׺��� ���� ��� �ܰ� ���̺�(TBRA_REPT_BALANCE) �� ����Ǹ� ���� �� û�� �� �ܰ� �ݿ��Ͽ� û����
    (����/���� ó���� ������. ���� :  ���� �� �������� ������ ��)
    �������� : �й��� ��� ������ �Աݳ����� �Աݱݾ��� �й�� �ݾ��� ���պ��� ���� ���
    �������� : �Աݳ��� ���Ŀ� ���ε� ������ ���� ���
    Input
    ACCN_NUM (���� ��ȣ)
    BALANCE (�ܾ�)
    BANK_CD (���� �ڵ�)
    BRAN_CD (���� �ڵ�)
    CLAIM_GBN (ä�� �Աݱ���)
    COMIS (������)
    DEMD_MMCNT (û��������)
    END_YRMN (������)
    IUDFLAG (���ڵ���±���)
    NOTE_YRMN (���� ���)
    RECV_DAY (���� ����)
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
    public DOBJ CTLrept_bankbook_insert(DOBJ dobj)
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
            ////
            String   TEMP1 ="0";         //�ӽ��÷�1
            dobj.setGVValue("TEMP1",TEMP1);
            dobj  = CALLrept_bankbook_insert_MIUD1(Conn, dobj);           //  �ο���� ó��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLrept_bankbook_insert_SEL10(Conn, dobj);           //  �������� ��ȸ
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
    public DOBJ CTLrept_bankbook_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        ////
        String   TEMP1 ="0";         //�ӽ��÷�1
        dobj.setGVValue("TEMP1",TEMP1);
        dobj  = CALLrept_bankbook_insert_MIUD1(Conn, dobj);           //  �ο���� ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLrept_bankbook_insert_SEL10(Conn, dobj);           //  �������� ��ȸ
        return dobj;
    }
    // �ο���� ó��
    public DOBJ CALLrept_bankbook_insert_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_bankbook_insert_XIUD1(Conn, dobj);           //  �������� ����
                dobj  = CALLrept_bankbook_insert_SEL1(Conn, dobj);           //  �Է�/���� ����
                dobj  = CALLrept_bankbook_insert_SEL2(Conn, dobj);           //  �Ա� ������ ����
                if( dobj.getRetObject("SEL1").getRecord().get("CRUD").equals("U"))
                {
                    dobj  = CALLrept_bankbook_insert_SEL3(Conn, dobj);           //  ���� �������� ����
                    if( dobj.getRetObject("SEL3").getRecord().get("ACCU_GBN").equals("22"))
                    {
                        dobj  = CALLrept_bankbook_insert_OSP1(Conn, dobj);           //  ��� ���� ����
                        dobj  = CALLrept_bankbook_insert_SEL5(Conn, dobj);           //  �ű� ������ ���Ȯ��
                        if(!dobj.getRetObject("SEL5").getRecord().get("ACCU_DAY").equals(""))
                        {
                            dobj  = CALLrept_bankbook_insert_OSP4(Conn, dobj);           //  ��� ���� �Ա�
                        }
                        else
                        {
                            dobj  = CALLrept_bankbook_insert_OSP5(Conn, dobj);           //  ���� �Ա�
                        }
                    }
                    else
                    {
                        dobj  = CALLrept_bankbook_insert_OSP2(Conn, dobj);           //  ���� �Ա� ����
                        dobj  = CALLrept_bankbook_insert_SEL5(Conn, dobj);           //  �ű� ������ ���Ȯ��
                        if(!dobj.getRetObject("SEL5").getRecord().get("ACCU_DAY").equals(""))
                        {
                            dobj  = CALLrept_bankbook_insert_OSP4(Conn, dobj);           //  ��� ���� �Ա�
                        }
                        else
                        {
                            dobj  = CALLrept_bankbook_insert_OSP5(Conn, dobj);           //  ���� �Ա�
                        }
                    }
                }
                else if( dobj.getRetObject("SEL1").getRecord().get("CRUD").equals("I"))
                {
                    dobj  = CALLrept_bankbook_insert_SEL7(Conn, dobj);           //  ���Ȯ��
                    if(!dobj.getRetObject("SEL7").getRecord().get("ACCU_DAY").equals(""))
                    {
                        dobj  = CALLrept_bankbook_insert_OSP7(Conn, dobj);           //  ��� ���� �Ա�
                    }
                    else
                    {
                        dobj  = CALLrept_bankbook_insert_OSP8(Conn, dobj);           //  ���� �Ա�
                    }
                }
                else
                {
                    dobj  = CALLrept_bankbook_insert_SEL8(Conn, dobj);           //  ���Ȯ��
                    if( dobj.getRetObject("SEL8").getRecord().get("ACCU_GBN").equals("22"))
                    {
                        dobj  = CALLrept_bankbook_insert_OSP9(Conn, dobj);           //  ��� ����
                    }
                    else
                    {
                        dobj  = CALLrept_bankbook_insert_OSP10(Conn, dobj);           //  ���� �Ա� ����
                    }
                }
            }
        }
        return dobj;
    }
    // �������� ����
    public DOBJ CALLrept_bankbook_insert_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_XIUD1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLrept_bankbook_insert_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE GIBU.TBRA_REPT_ERR  \n";
        query +=" WHERE REPT_DAY = :REPT_DAY  \n";
        query +=" AND REPT_GBN = :REPT_GBN  \n";
        query +=" AND BRAN_CD = :BRAN_CD  \n";
        query +=" AND	INSPRES_ID = :INSPRES_ID";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �Է�/���� ����
    // �Է� : �ű� �����ڵ� != NULL ���� : �ű� �����ڵ� == NULL ���� : �ű� �����ڵ� != ���� �����ڵ�
    public DOBJ CALLrept_bankbook_insert_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(:UPSO_CD,  NULL,  'D',  DECODE(A.UPSO_CD,  NULL,  'I',  'U'))  AS  CRUD  ,  A.UPSO_CD  BEFORE_UPSO_CD  ,  GIBU.FT_GET_LAST_REPT_YRMN(A.UPSO_CD,  6)  AS  LAST_YRMN  ,  :UPSO_CD  UPSO_CD  FROM  GIBU.TBRA_REPT  A  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ա� ������ ����
    // ���ν����� ȣ���ϱ� ���� �Աݵ����͸� �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        double   COMIS = dobj.getRetObject("R").getRecord().getDouble("COMIS");   //������
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BEFORE_UPSO_CD = dobj.getRetObject("SEL1").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   BANK_CD = dobj.getRetObject("R").getRecord().get("BANK_CD");   //���� �ڵ�
        double   DEMD_MMCNT = dobj.getRetObject("R").getRecord().getDouble("DEMD_MMCNT");   //û��������
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   START_YRMN = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   CRUD = dobj.getRetObject("SEL1").getRecord().get("CRUD");   //CRUD
        String   MAPPING_DAY = dobj.getRetObject("R").getRecord().get("MAPPING_DAY");   //���� ����
        String   NOTE_YRMN = dobj.getRetObject("R").getRecord().get("NOTE_YRMN");   //���� ���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   REPT_AMT = dobj.getRetObject("R").getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        double   NESS_AMT = dobj.getRetObject("R").getRecord().getDouble("NESS_AMT");   //�ʿ� �ݾ�
        String   RECV_DAY = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //���� ����
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   ACCN_NUM = dobj.getRetObject("R").getRecord().get("ACCN_NUM");   //���� ��ȣ
        double   BALANCE = dobj.getRetObject("R").getRecord().getDouble("BALANCE");   //�ܾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :NOTE_YRMN  AS  NOTE_YRMN  ,  NULL  AS  DISTR_SEQ  ,  :BRAN_CD  AS  BRAN_CD  ,  DECODE(:UPSO_CD,  NULL,  :BEFORE_UPSO_CD,  :UPSO_CD)  AS  UPSO_CD  ,  :DEMD_MMCNT  AS  DEMD_MMCNT  ,  SUBSTR(:START_YRMN,  1,  6)  AS  START_YRMN  ,  SUBSTR(:END_YRMN,  1,  6)  AS  END_YRMN  ,  :REPT_AMT  AS  REPT_AMT  ,  :COMIS  AS  COMIS  ,  :BALANCE  AS  BALANCE  ,  :RECV_DAY  AS  RECV_DAY  ,  :BANK_CD  AS  BANK_CD  ,  :ACCN_NUM  AS  ACCN_NUM  ,  :MAPPING_DAY  AS  MAPPING_DAY  ,  NULL  AS  DISTR_GBN  ,  NULL  AS  REMAK  ,  NULL  AS  CLAIM_GBN  ,  :CRUD  AS  CRUD  ,  :INSPRES_ID  AS  INSPRES_ID  ,  :NESS_AMT  AS  NESS_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //û��������
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("CRUD", CRUD);               //CRUD
        sobj.setString("MAPPING_DAY", MAPPING_DAY);               //���� ����
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //���� ���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setDouble("NESS_AMT", NESS_AMT);               //�ʿ� �ݾ�
        sobj.setString("RECV_DAY", RECV_DAY);               //���� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setDouble("BALANCE", BALANCE);               //�ܾ�
        return sobj;
    }
    // ���� �������� ����
    public DOBJ CALLrept_bankbook_insert_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   BEFORE_UPSO_CD = dobj.getRetObject("SEL1").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        double   NESS_AMT = dobj.getRetObject("R").getRecord().getDouble("NESS_AMT");   //�ʿ� �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  (   \n";
        query +=" SELECT  MAX(NOTE_YRMN)  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  A.UPSO_CD  )  NOTE_YRMN  ,  A.BRAN_CD  AS  BRAN_CD  ,  A.UPSO_CD  AS  UPSO_CD  ,  NULL  AS  DISTR_SEQ  ,  NULL  AS  DEMD_MMCNT  ,  NULL  AS  START_YRMN  ,  NULL  AS  END_YRMN  ,  NULL  AS  REPT_AMT  ,  NULL  AS  COMIS  ,  NULL  AS  BALANCE  ,  NULL  AS  RECV_DAY  ,  NULL  AS  BANK_CD  ,  NULL  AS  ACCN_NUM  ,  NULL  AS  DISTR_GBN  ,  NULL  AS  CLAIM_GBN  ,  NULL  AS  REMAK  ,  'D'  AS  CRUD  ,  :INSPRES_ID  AS  INSPRES_ID  ,  (   \n";
        query +=" SELECT  MAX(ACCU_GBN)  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :BEFORE_UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  ACCU_GBN  ,  :NESS_AMT  AS  NESS_AMT  FROM  GIBU.TBRA_REPT  A  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setDouble("NESS_AMT", NESS_AMT);               //�ʿ� �ݾ�
        return sobj;
    }
    // ��� ���� ����
    // ���� ������ �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL3");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP1");
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
        String[] outcolnms={"CNT_INST"};;
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
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �ű� ������ ���Ȯ��
    public DOBJ CALLrept_bankbook_insert_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  JUDG_CD  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  (   \n";
        query +=" SELECT  COUNT(YRMN)  FROM  GIBU.COPY_YRMN  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  A.SOL_START_YRMN   \n";
        query +=" AND  A.SOL_END_YRMN  )  DEMD_MMCNT  FROM  GIBU.TBRA_ACCU  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��� ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_OSP4(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP4");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP4");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRUD ="I";         //CRUD
            record.put("CRUD",CRUD);
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
        String[] outcolnms={"CNT_INST"};;
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
        robj.Println("OSP4");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_OSP5(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP5");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP5");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRUD ="I";         //CRUD
            record.put("CRUD",CRUD);
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
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
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
        robj.Println("OSP5");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա� ����
    // ���� ������ �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_OSP2(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP2");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL3");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
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
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
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
        robj.Println("OSP2");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���Ȯ��
    public DOBJ CALLrept_bankbook_insert_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.Println("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��� ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_OSP7(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP7");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP7");
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
        String[] outcolnms={"CNT_INST"};;
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
        robj.setName("OSP7");
        robj.Println("OSP7");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_OSP8(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP8");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP8");
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
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
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
        robj.setName("OSP8");
        robj.Println("OSP8");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���Ȯ��
    public DOBJ CALLrept_bankbook_insert_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_SEL8(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   UPSO_CD = dobj.getRetObject("SEL1").getRecord().get("BEFORE_UPSO_CD");   //���� �ڵ�
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
    // ��� ����
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_OSP9(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP9");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP9");
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
        String[] outcolnms={"CNT_INST"};;
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
        robj.setName("OSP9");
        robj.Println("OSP9");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա� ����
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_OSP10(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP10");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP10");
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
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
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
        robj.setName("OSP10");
        robj.Println("OSP10");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �������� ��ȸ
    public DOBJ CALLrept_bankbook_insert_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_SEL10(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.setRetcode(1);
        rvobj.Println("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("S").firstRecord().get("REPT_GBN");   //�Ա� ����
        String   CNT_INST = dobj.getGVString("TEMP1");   //CNT_INST
        String   BRAN_CD = dobj.getRetObject("S").firstRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  UPSO_CD  ,  START_YRMN  ,  END_YRMN  ,  REPT_AMT  ,  COMIS  ,  RECV_DAY  ,  ACCN_NUM  ,  ERR_GBN  ,  ERR_CTENT  FROM  GIBU.TBRA_REPT_ERR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  '1'  =  DECODE(:CNT_INST,  1,  NULL,  '1') ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("CNT_INST", CNT_INST);               //CNT_INST
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_bankbook_insert
    //##**$$rept_distr_insert02
    /* * ���α׷��� : bra04_s06
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/02
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_distr_insert02(DOBJ dobj)
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
            dobj  = CALLrept_distr_insert02_MPD47(Conn, dobj);           //  ���Ҹ�������
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLrept_distr_insert02_SEL6(Conn, dobj);           //  ���� �й� ���� ��ȸ
            dobj  = CALLrept_distr_insert02_SEL42(Conn, dobj);           //  �������� ��ȸ
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
    public DOBJ CTLrept_distr_insert02( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_distr_insert02_MPD47(Conn, dobj);           //  ���Ҹ�������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLrept_distr_insert02_SEL6(Conn, dobj);           //  ���� �й� ���� ��ȸ
        dobj  = CALLrept_distr_insert02_SEL42(Conn, dobj);           //  �������� ��ȸ
        return dobj;
    }
    // ���Ҹ�������
    public DOBJ CALLrept_distr_insert02_MPD47(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD47");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_distr_insert02_SEL31(Conn, dobj);           //  ���� �ڵ� Ȯ��
                if(dobj.getRetObject("R").getRecord().get("IUDFLAG").equals("D") && !dobj.getRetObject("SEL31").getRecord().get("UPSO_CD").equals(""))
                {
                    dobj  = CALLrept_distr_insert02_DEL84(Conn, dobj);           //  ���� ���� ����
                    dobj  = CALLrept_distr_insert02_SEL34(Conn, dobj);           //  �Ա� ���� Ȯ��
                    if( dobj.getRetObject("SEL34").getRecord().getDouble("REPT_CNT") == 0)
                    {
                        dobj  = CALLrept_distr_insert02_DEL36(Conn, dobj);           //  �Ա� ���� ���� ���� �ʱ�ȭ
                        dobj  = CALLrept_distr_insert02_DEL37(Conn, dobj);           //  �������� ����
                        dobj  = CALLrept_distr_insert02_UPD38(Conn, dobj);           //  �Ա� ���� ����
                        dobj  = CALLrept_distr_insert02_UPD39(Conn, dobj);           //  �Ա� ���� ����
                        dobj  = CALLrept_distr_insert02_SEL40(Conn, dobj);           //  ���� �Ա����� ��ȸ
                        if( dobj.getRetObject("SEL40").getRecord().getDouble("CNT") == 0)
                        {
                            dobj  = CALLrept_distr_insert02_UPD42(Conn, dobj);           //  ���� �ű��� ����
                        }
                    }
                    else
                    {
                        dobj  = CALLrept_distr_insert02_DEL61(Conn, dobj);           //  �������� �ʱ�ȭ
                        dobj  = CALLrept_distr_insert02_XIUD62(Conn, dobj);           //  �������� ����
                    }
                }
                else if(!dobj.getRetObject("R").getRecord().get("UPSO_CD").equals("") && dobj.getRetObject("SEL31").getRecord().get("UPSO_CD").equals(""))
                {
                    dobj  = CALLrept_distr_insert02_INS42(Conn, dobj);           //  �������� �Է�
                    dobj  = CALLrept_distr_insert02_SEL1(Conn, dobj);           //  ����� ����Ȯ��
                    if( dobj.getRetObject("SEL1").getRecord().getDouble("ACCU_CNT") == 0)
                    {
                        dobj  = CALLrept_distr_insert02_OSP1(Conn, dobj);           //  ������ �Ա� ó��
                        if(!dobj.getRetObject("OSP1").getRecord().get("CNT_INST").equals("1"))
                        {
                            ////
                            String UPSO_CD="";   //���� �ڵ�
                            if( dobj.getGVString ("UPSO_CD").equals(""))
                            {
                                UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");
                            }
                            else
                            {
                                UPSO_CD = dobj.getGVString("UPSO_CD")+", "+dobj.getRetObject("R").getRecord().get("UPSO_CD");
                            }
                            dobj.setGVValue("UPSO_CD",UPSO_CD);
                        }
                    }
                    else
                    {
                        dobj  = CALLrept_distr_insert02_OSP63(Conn, dobj);           //  ������ �Ա� ó��
                        if(!dobj.getRetObject("OSP63").getRecord().get("CNT_INST").equals("1"))
                        {
                            ////
                            String UPSO_CD="";   //���� �ڵ�
                            if( dobj.getGVString ("UPSO_CD").equals(""))
                            {
                                UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");
                            }
                            else
                            {
                                UPSO_CD = dobj.getGVString("UPSO_CD")+", "+dobj.getRetObject("R").getRecord().get("UPSO_CD");
                            }
                            dobj.setGVValue("UPSO_CD",UPSO_CD);
                        }
                    }
                }
                else if(!dobj.getRetObject("R").getRecord().get("UPSO_CD").equals("") && !dobj.getRetObject("R").getRecord().get("UPSO_CD").equals(dobj.getRetObject("SEL31").getRecord().get("UPSO_CD")))
                {
                    dobj  = CALLrept_distr_insert02_UPD88(Conn, dobj);           //  ���� ���� ����
                    dobj  = CALLrept_distr_insert02_SEL45(Conn, dobj);           //  �Ա� ���� Ȯ��
                    if( dobj.getRetObject("SEL45").getRecord().getDouble("REPT_CNT") == 0)
                    {
                        dobj  = CALLrept_distr_insert02_DEL47(Conn, dobj);           //  �Ա� ���� ���� ���� �ʱ�ȭ
                        dobj  = CALLrept_distr_insert02_DEL48(Conn, dobj);           //  �������� ����
                        dobj  = CALLrept_distr_insert02_UPD50(Conn, dobj);           //  �Ա� ���� ����
                        dobj  = CALLrept_distr_insert02_UPD51(Conn, dobj);           //  �Ա� ���� ����
                        dobj  = CALLrept_distr_insert02_SEL52(Conn, dobj);           //  ���� �Ա����� ��ȸ
                        if( dobj.getRetObject("SEL52").getRecord().getDouble("CNT") == 0)
                        {
                            dobj  = CALLrept_distr_insert02_UPD54(Conn, dobj);           //  ���� �ű��� ����
                            dobj  = CALLrept_distr_insert02_SEL55(Conn, dobj);           //  ����� ����Ȯ��
                            if( dobj.getRetObject("SEL55").getRecord().getDouble("ACCU_CNT") == 0)
                            {
                                dobj  = CALLrept_distr_insert02_OSP57(Conn, dobj);           //  ������ �Ա� ó��
                                if(!dobj.getRetObject("OSP57").getRecord().get("CNT_INST").equals("1"))
                                {
                                    ////
                                    String UPSO_CD="";   //���� �ڵ�
                                    if( dobj.getGVString ("UPSO_CD").equals(""))
                                    {
                                        UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");
                                    }
                                    else
                                    {
                                        UPSO_CD = dobj.getGVString("UPSO_CD")+", "+dobj.getRetObject("R").getRecord().get("UPSO_CD");
                                    }
                                    dobj.setGVValue("UPSO_CD",UPSO_CD);
                                }
                            }
                            else
                            {
                                dobj  = CALLrept_distr_insert02_OSP64(Conn, dobj);           //  ������ �Ա� ó��
                                if(!dobj.getRetObject("OSP64").getRecord().get("CNT_INST").equals("1"))
                                {
                                    ////
                                    String UPSO_CD="";   //���� �ڵ�
                                    if( dobj.getGVString ("UPSO_CD").equals(""))
                                    {
                                        UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");
                                    }
                                    else
                                    {
                                        UPSO_CD = dobj.getGVString("UPSO_CD")+", "+dobj.getRetObject("R").getRecord().get("UPSO_CD");
                                    }
                                    dobj.setGVValue("UPSO_CD",UPSO_CD);
                                }
                            }
                        }
                    }
                    else
                    {
                        dobj  = CALLrept_distr_insert02_DEL59(Conn, dobj);           //  �������� �ʱ�ȭ
                        dobj  = CALLrept_distr_insert02_XIUD60(Conn, dobj);           //  �������� ����
                    }
                }
            }
        }
        return dobj;
    }
    // ���� �ڵ� Ȯ��
    public DOBJ CALLrept_distr_insert02_SEL31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL31");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL31");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_insert02_SEL31(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL31");
        rvobj.Println("SEL31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_SEL31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   DISTR_SEQ = dobj.getRetObject("S").getRecord().get("DISTR_SEQ");   //�й� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  DISTR_SEQ  =  :DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //�й� ����
        return sobj;
    }
    // ���� ���� ����
    public DOBJ CALLrept_distr_insert02_DEL84(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL84");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL84");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_DEL84(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL84") ;
        rvobj.Println("DEL84");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_DEL84(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DISTR_SEQ = dvobj.getRecord().get("DISTR_SEQ");   //�й� ����
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_UPSO_DISTR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN  \n";
        query +=" and DISTR_SEQ=:DISTR_SEQ";
        sobj.setSql(query);
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //�й� ����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �Ա� ���� Ȯ��
    // �ش� �Ա��� ���ķ� �Աݵ� ������ �ִ��� Ȯ���Ѵ�.  �Ա� ������ �ִ� ��� ������ �� ����
    public DOBJ CALLrept_distr_insert02_SEL34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL34");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL34");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_insert02_SEL34(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL34");
        rvobj.Println("SEL34");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_SEL34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(UPSO_CD)  REPT_CNT  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  (REPT_DAY  ||  REPT_NUM)  >  (:REPT_DAY  ||  :REPT_NUM) ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ա� ���� ���� ���� �ʱ�ȭ
    // �Ա� ���� ���� ���� �ʱ�ȭ
    public DOBJ CALLrept_distr_insert02_DEL36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL36");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL36");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_DEL36(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL36") ;
        rvobj.Println("DEL36");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_DEL36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_DEMD_REPT  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �������� ����
    // �������� ����
    public DOBJ CALLrept_distr_insert02_DEL37(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL37");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL37");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_DEL37(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL37") ;
        rvobj.Println("DEL37");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_DEL37(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NOTE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �Ա� ���� ����
    // �Ա� ���� ����
    public DOBJ CALLrept_distr_insert02_UPD38(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD38");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD38");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_UPD38(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD38") ;
        rvobj.Println("UPD38");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_UPD38(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   UPSO_CD ="";   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT_TRANS  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ա� ���� ����
    // �Ա� ���� ����
    public DOBJ CALLrept_distr_insert02_UPD39(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD39");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD39");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_UPD39(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD39") ;
        rvobj.Println("UPD39");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_UPD39(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   REPT_GBN ="03";   //�Ա� ����
        String   UPSO_CD ="";   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �Ա����� ��ȸ
    public DOBJ CALLrept_distr_insert02_SEL40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL40");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL40");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_insert02_SEL40(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL40");
        rvobj.Println("SEL40");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_SEL40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(UPSO_CD)  CNT  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �ű��� ����
    // ���� �ű��� ����
    public DOBJ CALLrept_distr_insert02_UPD42(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD42");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD42");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_UPD42(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD42") ;
        rvobj.Println("UPD42");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_UPD42(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   NEW_DAY ="";   //�ű� ����
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , NEW_DAY=:NEW_DAY , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("NEW_DAY", NEW_DAY);               //�ű� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������� �ʱ�ȭ
    // �������� �ʱ�ȭ
    public DOBJ CALLrept_distr_insert02_DEL61(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL61");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL61");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_DEL61(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL61") ;
        rvobj.Println("DEL61");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_DEL61(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_ERR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �������� ����
    public DOBJ CALLrept_distr_insert02_XIUD62(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD62");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD62");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_XIUD62(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD62");
        rvobj.Println("XIUD62");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_XIUD62(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   COMIS = dobj.getRetObject("R").getRecord().getDouble("COMIS");   //������
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   INSPRES_ID = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //����� ID
        double   REPT_AMT = dobj.getRetObject("R").getRecord().getDouble("DISTR_AMT");   //�Ա� �ݾ�
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   START_YRMN = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_REPT_ERR ( REPT_DAY , REPT_NUM , REPT_GBN , UPSO_CD , START_YRMN , END_YRMN , REPT_AMT , COMIS , RECV_DAY , BANK_CD , ACCN_NUM , BRAN_CD , ERR_GBN , ERR_CTENT , INSPRES_ID , INS_DATE) SELECT REPT_DAY , REPT_NUM , REPT_GBN , :UPSO_CD , :START_YRMN , :END_YRMN , :REPT_AMT , :COMIS , RECV_DAY , BANK_CD , ACCN_NUM , BRAN_CD , :ERR_CODE , :ERR_MESG , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_REPT_TRANS WHERE REPT_DAY = :REPT_DAY AND REPT_NUM = :REPT_NUM";
        sobj.setSql(query);
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������� �Է�
    public DOBJ CALLrept_distr_insert02_INS42(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS42");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS42");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_INS42(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS42") ;
        rvobj.Println("INS42");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_INS42(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String DISTR_SEQ ="";        //�й� ����
        String INS_DATE ="";        //��� �Ͻ�
        String   DISTR_SEQ_3 = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�й� ����
        String   DISTR_SEQ_2 = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�й� ����
        String   DISTR_SEQ_1 = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�й� ����
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        double   DISTR_AMT = dvobj.getRecord().getDouble("DISTR_AMT");   //�й� �ݾ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_UPSO_DISTR (REPT_DAY, INS_DATE, REPT_NUM, INSPRES_ID, DISTR_AMT, REPT_GBN, UPSO_CD, REMAK, DISTR_SEQ)  \n";
        query +=" values(:REPT_DAY , SYSDATE, :REPT_NUM , :INSPRES_ID , :DISTR_AMT , :REPT_GBN , :UPSO_CD , :REMAK , (SELECT NVL(MAX(DISTR_SEQ), 0) + 1 DISTR_SEQ FROM GIBU.TBRA_REPT_UPSO_DISTR WHERE REPT_DAY = :DISTR_SEQ_1 AND REPT_NUM = :DISTR_SEQ_2 AND REPT_GBN = :DISTR_SEQ_3))";
        sobj.setSql(query);
        sobj.setString("DISTR_SEQ_3", DISTR_SEQ_3);               //�й� ����
        sobj.setString("DISTR_SEQ_2", DISTR_SEQ_2);               //�й� ����
        sobj.setString("DISTR_SEQ_1", DISTR_SEQ_1);               //�й� ����
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setDouble("DISTR_AMT", DISTR_AMT);               //�й� �ݾ�
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ����� ����Ȯ��
    // ������� ��ü���� Ȯ���Ѵ�
    public DOBJ CALLrept_distr_insert02_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_insert02_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL   \n";
        query +=" AND  JUDG_CD  ='1' ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������ �Ա� ó��
    // ������ �Ա� ó��
    public DOBJ CALLrept_distr_insert02_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP1");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","UPSO_CD","START_YRMN","END_YRMN","DISTR_AMT","COMIS","DISTR_GBN","REMAK","INSPRES_ID"};
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
        String   spname ="GIBU.SP_PROC_REPT_TRANS";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
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
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ������ �Ա� ó��
    // ������ �Ա� ó��
    public DOBJ CALLrept_distr_insert02_OSP63(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP63");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP63");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","UPSO_CD","START_YRMN","END_YRMN","DISTR_AMT","COMIS","DISTR_GBN","REMAK","INSPRES_ID"};
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
        String   spname ="GIBU.SP_PROC_REPT_TRANS_ACCU";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
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
        robj.setName("OSP63");
        robj.Println("OSP63");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� ���� ����
    public DOBJ CALLrept_distr_insert02_UPD88(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD88");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD88");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_UPD88(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD88") ;
        rvobj.Println("UPD88");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_UPD88(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //��� �Ͻ�
        String   DISTR_SEQ = dvobj.getRecord().get("DISTR_SEQ");   //�й� ����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        double   DISTR_AMT = dvobj.getRecord().getDouble("DISTR_AMT");   //�й� �ݾ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT_UPSO_DISTR  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , DISTR_AMT=:DISTR_AMT , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN  \n";
        query +=" and DISTR_SEQ=:DISTR_SEQ";
        sobj.setSql(query);
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //�й� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setDouble("DISTR_AMT", DISTR_AMT);               //�й� �ݾ�
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //����� ID
        return sobj;
    }
    // �Ա� ���� Ȯ��
    // �ش� �Ա��� ���ķ� �Աݵ� ������ �ִ��� Ȯ���Ѵ�.  �Ա� ������ �ִ� ��� ������ �� ����
    public DOBJ CALLrept_distr_insert02_SEL45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL45");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL45");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_insert02_SEL45(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL45");
        rvobj.Println("SEL45");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_SEL45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(UPSO_CD)  REPT_CNT  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  (REPT_DAY  ||  REPT_NUM)  >  (:REPT_DAY  ||  :REPT_NUM) ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ա� ���� ���� ���� �ʱ�ȭ
    // �Ա� ���� ���� ���� �ʱ�ȭ
    public DOBJ CALLrept_distr_insert02_DEL47(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL47");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL47");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_DEL47(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL47") ;
        rvobj.Println("DEL47");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_DEL47(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_DEMD_REPT  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �������� ����
    // �������� ����
    public DOBJ CALLrept_distr_insert02_DEL48(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL48");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL48");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_DEL48(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL48") ;
        rvobj.Println("DEL48");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_DEL48(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NOTE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �Ա� ���� ����
    // �Ա� ���� ����
    public DOBJ CALLrept_distr_insert02_UPD50(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD50");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD50");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_UPD50(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD50") ;
        rvobj.Println("UPD50");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_UPD50(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   UPSO_CD ="";   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT_TRANS  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ա� ���� ����
    // �Ա� ���� ����
    public DOBJ CALLrept_distr_insert02_UPD51(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD51");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD51");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_UPD51(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD51") ;
        rvobj.Println("UPD51");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_UPD51(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   REPT_GBN ="03";   //�Ա� ����
        String   UPSO_CD ="";   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �Ա����� ��ȸ
    public DOBJ CALLrept_distr_insert02_SEL52(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL52");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL52");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_insert02_SEL52(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL52");
        rvobj.Println("SEL52");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_SEL52(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(UPSO_CD)  CNT  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �ű��� ����
    // ���� �ű��� ����
    public DOBJ CALLrept_distr_insert02_UPD54(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD54");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD54");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_UPD54(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD54") ;
        rvobj.Println("UPD54");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_UPD54(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   NEW_DAY ="";   //�ű� ����
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , NEW_DAY=:NEW_DAY , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("NEW_DAY", NEW_DAY);               //�ű� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ����� ����Ȯ��
    // ������� ��ü���� Ȯ���Ѵ�
    public DOBJ CALLrept_distr_insert02_SEL55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL55");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL55");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_insert02_SEL55(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL55");
        rvobj.Println("SEL55");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_SEL55(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL   \n";
        query +=" AND  JUDG_CD  ='1' ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������ �Ա� ó��
    // ������ �Ա� ó��
    public DOBJ CALLrept_distr_insert02_OSP57(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP57");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP57");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","UPSO_CD","START_YRMN","END_YRMN","DISTR_AMT","COMIS","DISTR_GBN","REMAK","INSPRES_ID"};
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
        String   spname ="GIBU.SP_PROC_REPT_TRANS";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
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
        robj.setName("OSP57");
        robj.Println("OSP57");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ������ �Ա� ó��
    // ������ �Ա� ó��
    public DOBJ CALLrept_distr_insert02_OSP64(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP64");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP64");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","UPSO_CD","START_YRMN","END_YRMN","DISTR_AMT","COMIS","DISTR_GBN","REMAK","INSPRES_ID"};
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
        String   spname ="GIBU.SP_PROC_REPT_TRANS_ACCU";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
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
        robj.setName("OSP64");
        robj.Println("OSP64");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �������� �ʱ�ȭ
    // �������� �ʱ�ȭ
    public DOBJ CALLrept_distr_insert02_DEL59(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL59");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL59");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_DEL59(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL59") ;
        rvobj.Println("DEL59");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_DEL59(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_ERR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �������� ����
    public DOBJ CALLrept_distr_insert02_XIUD60(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD60");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD60");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert02_XIUD60(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD60");
        rvobj.Println("XIUD60");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_XIUD60(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   COMIS = dobj.getRetObject("R").getRecord().getDouble("COMIS");   //������
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   REPT_AMT = dobj.getRetObject("R").getRecord().getDouble("DISTR_AMT");   //�Ա� �ݾ�
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   START_YRMN = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_REPT_ERR ( REPT_DAY , REPT_NUM , REPT_GBN , UPSO_CD , START_YRMN , END_YRMN , REPT_AMT , COMIS , RECV_DAY , BANK_CD , ACCN_NUM , BRAN_CD , ERR_GBN , ERR_CTENT , INSPRES_ID , INS_DATE) SELECT REPT_DAY , REPT_NUM , REPT_GBN , :UPSO_CD , :START_YRMN , :END_YRMN , :REPT_AMT , :COMIS , RECV_DAY , BANK_CD , ACCN_NUM , BRAN_CD , :ERR_CODE , :ERR_MESG , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_REPT_TRANS WHERE REPT_DAY = :REPT_DAY AND REPT_NUM = :REPT_NUM";
        sobj.setSql(query);
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �й� ���� ��ȸ
    // ���� �й� ���� ��ȸ
    public DOBJ CALLrept_distr_insert02_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_insert02_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.setRetcode(1);
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.DISTR_SEQ  ,  B.UPSO_CD  ,  C.UPSO_NM  ,  B.START_YRMN  ,  B.END_YRMN  ,  A.DISTR_AMT  ,  A.REMAK  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MIN(DEMD_YRMN)  START_YRMN  ,  MAX(DEMD_YRMN)  END_YRMN  FROM  GIBU.TBRA_DEMD_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  GROUP  BY  UPSO_CD  )  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        return sobj;
    }
    // �������� ��ȸ
    public DOBJ CALLrept_distr_insert02_SEL42(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL42");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL42");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_insert02_SEL42(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL42");
        rvobj.setRetcode(1);
        rvobj.Println("SEL42");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert02_SEL42(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("S").firstRecord().get("REPT_GBN");   //�Ա� ����
        String   UPSO_CD = dobj.getGVString("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  UPSO_CD  ,  START_YRMN  ,  END_YRMN  ,  REPT_AMT  ,  COMIS  ,  RECV_DAY  ,  ACCN_NUM  ,  ERR_GBN  ,  ERR_CTENT  FROM  GIBU.TBRA_REPT_ERR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  UPSO_CD  IN  (:UPSO_CD) ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_distr_insert02
    //##**$$rept_bran_distr_select
    /* * ���α׷��� : bra04_s06
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/02
    * ����    : �й�� �й賻���� ��ȸ�Ѵ�.
    Input
    BRAN_CD (���� �ڵ�)
    DISTR_GBN (�Ա� �й� ����)
    END_DAY (������)
    REPT_DAY (�Ա�����)
    REPT_NUM (�Ա� ��ȣ)
    START_DAY (������)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_bran_distr_select(DOBJ dobj)
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
            dobj  = CALLrept_bran_distr_select_SEL1(Conn, dobj);           //  ���� �й� ���� ��ȸ
            if( dobj.getRetObject("S").getRecord().get("REPT_NUM").equals(""))
            {
                dobj  = CALLrept_bran_distr_select_SEL2(Conn, dobj);           //  ���� �й� ���� ��ȸ
                dobj  = CALLrept_bran_distr_select_MRG1( dobj);        //  ���� �й� ���� ��ȸ ����
            }
            else
            {
                dobj  = CALLrept_bran_distr_select_SEL3(Conn, dobj);           //  ���� �й� ���� ��ȸ
                dobj  = CALLrept_bran_distr_select_MRG1( dobj);        //  ���� �й� ���� ��ȸ ����
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
    public DOBJ CTLrept_bran_distr_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_bran_distr_select_SEL1(Conn, dobj);           //  ���� �й� ���� ��ȸ
        if( dobj.getRetObject("S").getRecord().get("REPT_NUM").equals(""))
        {
            dobj  = CALLrept_bran_distr_select_SEL2(Conn, dobj);           //  ���� �й� ���� ��ȸ
            dobj  = CALLrept_bran_distr_select_MRG1( dobj);        //  ���� �й� ���� ��ȸ ����
        }
        else
        {
            dobj  = CALLrept_bran_distr_select_SEL3(Conn, dobj);           //  ���� �й� ���� ��ȸ
            dobj  = CALLrept_bran_distr_select_MRG1( dobj);        //  ���� �й� ���� ��ȸ ����
        }
        return dobj;
    }
    // ���� �й� ���� ��ȸ
    // �Ա����ڸ� �������� ���� �й� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_bran_distr_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bran_distr_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bran_distr_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //���� ��ȣ
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  BANK_CD  ,  ACCN_NUM  ,  REPTPRES  ,  REPT_AMT  ,  OUT_AMT  ,  BALANCE  ,  RECV_DAY  ,  RECEPT_BANK  ,  BRAN_CD  ,  REMAK  ,  INSPRES_ID  ,  INS_DATE  FROM  GIBU.TBRA_REPT_TRANS  WHERE  RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  ACCN_NUM  =  :ACCN_NUM   \n";
        query +=" AND  DISTR_GBN  =  '42' ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    // ���� �й� ���� ��ȸ
    // �Ա����ڸ� �������� ���� �й� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_bran_distr_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bran_distr_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bran_distr_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("SEL1").firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").firstRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.DISTR_GBN  ,  A.DISTR_SEQ  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  C.UPSO_NM  ,  DECODE(B.START_YRMN,  NULL,  DECODE(D.NOTE_YRMN,  NULL,'',  TO_CHAR(ADD_MONTHS(TO_DATE(D.NOTE_YRMN,  'YYYYMM'),  1),  'YYYYMM')  ||  '01'),  B.START_YRMN)  START_YRMN  ,  B.END_YRMN  ,  A.DISTR_AMT  ,  D.NOTE_YRMN  ,  A.MAPPING_DAY  ,  A.REMAK  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(MIN(NOTE_YRMN),  NULL,  NULL,  MIN(NOTE_YRMN)  ||  '01')  START_YRMN  ,  DECODE(MAX(NOTE_YRMN),  NULL,  NULL,  MAX(NOTE_YRMN)  ||  '01')  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  B  ,  GIBU.TBRA_UPSO  C  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  ,  MAX(NOTE_YRMN)  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  )  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  D  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  B.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  B.DISTR_SEQ(+)  =  A.DISTR_SEQ   \n";
        query +=" AND  D.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  D.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  D.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  D.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  D.DISTR_SEQ(+)  =  A.DISTR_SEQ  ORDER  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN,  A.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �й� ���� ��ȸ ����
    public DOBJ CALLrept_bran_distr_select_MRG1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL2, SEL3","");
        rvobj.setName("MRG1") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ���� �й� ���� ��ȸ
    // �Ա����ڸ� �������� ���� �й� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_bran_distr_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bran_distr_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bran_distr_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.DISTR_GBN  ,  A.DISTR_SEQ  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  C.UPSO_NM  ,  DECODE(B.START_YRMN,  NULL,  DECODE(D.NOTE_YRMN,  NULL,'',  TO_CHAR(ADD_MONTHS(TO_DATE(D.NOTE_YRMN,  'YYYYMM'),  1),  'YYYYMM')  ||  '01'),  B.START_YRMN)  START_YRMN  ,  B.END_YRMN  ,  A.DISTR_AMT  ,  D.NOTE_YRMN  ,  A.MAPPING_DAY  ,  A.REMAK  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(MIN(NOTE_YRMN),  NULL,  NULL,  MIN(NOTE_YRMN)  ||  '01')  START_YRMN  ,  DECODE(MAX(NOTE_YRMN),  NULL,  NULL,  MAX(NOTE_YRMN)  ||  '01')  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  B  ,  GIBU.TBRA_UPSO  C  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  ,  MAX(NOTE_YRMN)  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  )  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  D  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  B.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  B.DISTR_SEQ(+)  =  A.DISTR_SEQ   \n";
        query +=" AND  D.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  D.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  D.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  D.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  D.DISTR_SEQ(+)  =  A.DISTR_SEQ  ORDER  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN,  A.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_bran_distr_select
    //##**$$rept_distr_insert
    /* * ���α׷��� : bra04_s06
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/11
    * ����    : ������ �Աݳ����� ���� ���ΰ� �й賻���� �����Ѵ�.
    �������� : ������ �Աݳ����� �Աݾ� = ���ΰ� �й賻���� �� ��
    Input
    BRAN_CD (���� �ڵ�)
    DISTR_AMT (�й� �ݾ�)
    INSPRES_ID (����� ID)
    IUDFLAG (���ڵ���±���)
    RECV_BRAN_CD (RECV_BRAN_CD)
    REPT_AMT (�Ա� �ݾ�)
    REPT_DAY (�Ա�����)
    REPT_GBN (�Ա� ����)
    REPT_NUM (�Ա� ��ȣ)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_distr_insert(DOBJ dobj)
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
            dobj  = CALLrept_distr_insert_SEL24(Conn, dobj);           //  �Ա����� Ȯ��
            if( dobj.getRetObject("SEL24").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLrept_distr_insert_DEL41(Conn, dobj);           //  ���� �й� ���� ����
                dobj  = CALLrept_distr_insert_INS2(Conn, dobj);           //  ���� �й� ���� ����
                dobj  = CALLrept_distr_insert_SEL10(Conn, dobj);           //  �й� �ݾ� ��ȸ
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="���ε� �Ա������� �ֽ��ϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
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
    public DOBJ CTLrept_distr_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_distr_insert_SEL24(Conn, dobj);           //  �Ա����� Ȯ��
        if( dobj.getRetObject("SEL24").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLrept_distr_insert_DEL41(Conn, dobj);           //  ���� �й� ���� ����
            dobj  = CALLrept_distr_insert_INS2(Conn, dobj);           //  ���� �й� ���� ����
            dobj  = CALLrept_distr_insert_SEL10(Conn, dobj);           //  �й� �ݾ� ��ȸ
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="���ε� �Ա������� �ֽ��ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �Ա����� Ȯ��
    public DOBJ CALLrept_distr_insert_SEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL24");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL24");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_insert_SEL24(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL24");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("S").firstRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(UPSO_CD)  CNT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // ���� �й� ���� ����
    // ���� �й� ���� ����
    public DOBJ CALLrept_distr_insert_DEL41(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL41");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert_DEL41(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL41") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert_DEL41(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_DISTR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // ���� �й� ���� ����
    // ���� �й� ���� ����
    public DOBJ CALLrept_distr_insert_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_distr_insert_INS2(dobj, dvobj);
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
    private SQLObject SQLrept_distr_insert_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        double   DISTR_AMT = dvobj.getRecord().getDouble("DISTR_AMT");   //�й� �ݾ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   BRAN_CD = dvobj.getRecord().get("RECV_BRAN_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_DISTR (REPT_DAY, INS_DATE, REPT_NUM, INSPRES_ID, DISTR_AMT, REPT_GBN, BRAN_CD, REMAK)  \n";
        query +=" values(:REPT_DAY , SYSDATE, :REPT_NUM , :INSPRES_ID , :DISTR_AMT , :REPT_GBN , :BRAN_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setDouble("DISTR_AMT", DISTR_AMT);               //�й� �ݾ�
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // �й� �ݾ� ��ȸ
    // �й� �ݾ� ��ȸ
    public DOBJ CALLrept_distr_insert_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_insert_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_insert_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dvobj.firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dvobj.firstRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(A.DISTR_AMT)  DISTR_AMT  ,  MAX(B.REPT_AMT)  REPT_AMT  FROM  GIBU.TBRA_REPT_DISTR  A  ,  GIBU.TBRA_REPT_TRANS  B  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    //##**$$rept_distr_insert
    //##**$$rept_distr_select
    /* * ���α׷��� : bra04_s06
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/30
    * ����    : ������ �Աݳ����� ���� ���ΰ� �й賻���� �й賻���� ���� ���Ұ� �й賻���� ��ȸ�Ѵ�.
    Input
    BRAN_CD (���� �ڵ�)
    END_DAY (������)
    REPT_DAY (�Ա�����)
    REPT_NUM (�Ա� ��ȣ)
    START_DAY (������)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_distr_select(DOBJ dobj)
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
            dobj  = CALLrept_distr_select_SEL1(Conn, dobj);           //  ���κй� �Ա����� ��ȸ
            if( dobj.getRetObject("S").getRecord().get("REPT_NUM").equals(""))
            {
                dobj  = CALLrept_distr_select_SEL2(Conn, dobj);           //  ���� �й� ���� ��ȸ
                dobj  = CALLrept_distr_select_MRG2( dobj);        //  ���� �й� ���� ��ȸ �������
                if( dobj.getRetObject("S").getRecord().get("REPT_NUM").equals(""))
                {
                    dobj  = CALLrept_distr_select_SEL3(Conn, dobj);           //  ���� �й� ���� ��ȸ
                    dobj  = CALLrept_distr_select_MRG7( dobj);        //  ���� �й� ���� ��ȸ ����
                }
                else
                {
                    dobj  = CALLrept_distr_select_SEL6(Conn, dobj);           //  ���� �й� ���� ��ȸ
                    dobj  = CALLrept_distr_select_MRG7( dobj);        //  ���� �й� ���� ��ȸ ����
                }
            }
            else
            {
                dobj  = CALLrept_distr_select_SEL15(Conn, dobj);           //  ���� �й� ���� ��ȸ
                dobj  = CALLrept_distr_select_MRG2( dobj);        //  ���� �й� ���� ��ȸ �������
                if( dobj.getRetObject("S").getRecord().get("REPT_NUM").equals(""))
                {
                    dobj  = CALLrept_distr_select_SEL3(Conn, dobj);           //  ���� �й� ���� ��ȸ
                    dobj  = CALLrept_distr_select_MRG7( dobj);        //  ���� �й� ���� ��ȸ ����
                }
                else
                {
                    dobj  = CALLrept_distr_select_SEL6(Conn, dobj);           //  ���� �й� ���� ��ȸ
                    dobj  = CALLrept_distr_select_MRG7( dobj);        //  ���� �й� ���� ��ȸ ����
                }
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
    public DOBJ CTLrept_distr_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_distr_select_SEL1(Conn, dobj);           //  ���κй� �Ա����� ��ȸ
        if( dobj.getRetObject("S").getRecord().get("REPT_NUM").equals(""))
        {
            dobj  = CALLrept_distr_select_SEL2(Conn, dobj);           //  ���� �й� ���� ��ȸ
            dobj  = CALLrept_distr_select_MRG2( dobj);        //  ���� �й� ���� ��ȸ �������
            if( dobj.getRetObject("S").getRecord().get("REPT_NUM").equals(""))
            {
                dobj  = CALLrept_distr_select_SEL3(Conn, dobj);           //  ���� �й� ���� ��ȸ
                dobj  = CALLrept_distr_select_MRG7( dobj);        //  ���� �й� ���� ��ȸ ����
            }
            else
            {
                dobj  = CALLrept_distr_select_SEL6(Conn, dobj);           //  ���� �й� ���� ��ȸ
                dobj  = CALLrept_distr_select_MRG7( dobj);        //  ���� �й� ���� ��ȸ ����
            }
        }
        else
        {
            dobj  = CALLrept_distr_select_SEL15(Conn, dobj);           //  ���� �й� ���� ��ȸ
            dobj  = CALLrept_distr_select_MRG2( dobj);        //  ���� �й� ���� ��ȸ �������
            if( dobj.getRetObject("S").getRecord().get("REPT_NUM").equals(""))
            {
                dobj  = CALLrept_distr_select_SEL3(Conn, dobj);           //  ���� �й� ���� ��ȸ
                dobj  = CALLrept_distr_select_MRG7( dobj);        //  ���� �й� ���� ��ȸ ����
            }
            else
            {
                dobj  = CALLrept_distr_select_SEL6(Conn, dobj);           //  ���� �й� ���� ��ȸ
                dobj  = CALLrept_distr_select_MRG7( dobj);        //  ���� �й� ���� ��ȸ ����
            }
        }
        return dobj;
    }
    // ���κй� �Ա����� ��ȸ
    // ���� �й�� ������ �Ա����� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_distr_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        String   BRAN_CD = dobj.getRetObject("S").firstRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.REPTPRES  ,  A.REPT_AMT  ,  A.RECV_DAY  ,  B.DEPT_NM  BRAN_NM  ,  A.BRAN_CD  ,  A.REMAK  ,  '03'  REPT_GBN  FROM  GIBU.TBRA_REPT_TRANS  A  ,  INSA.TCPM_DEPT  B  WHERE  A.RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.DISTR_GBN  =  '41'   \n";
        query +=" AND  B.GIBU  =  A.BRAN_CD  UNION   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.REPTPRES  ,  A.REPT_AMT  ,  A.RECV_DAY  ,  B.DEPT_NM  BRAN_NM  ,  A.BRAN_CD  ,  A.REMAK  ,  '03'  REPT_GBN  FROM  GIBU.TBRA_REPT_TRANS  A  ,  INSA.TCPM_DEPT  B  ,  GIBU.TBRA_REPT_DISTR  C  WHERE  A.REPT_DAY  =  C.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM   \n";
        query +=" AND  C.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.GIBU  =  A.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    // ���� �й� ���� ��ȸ
    // ���ο� �й�� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_distr_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("SEL1").firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.REPTPRES  ,  B.DISTR_AMT  ,  A.BALANCE  ,  A.RECV_DAY  ,  A.RECEPT_BANK  ,  B.BRAN_CD  RECV_BRAN_CD  ,  D.DEPT_NM  RECV_BRAN_NM  ,  C.DEPT_NM  BRAN_NM  ,  A.BRAN_CD  ,  B.REMAK  ,  A.INSPRES_ID  ,  A.INS_DATE  ,  '03'  REPT_GBN  ,  :BRAN_CD  S_BRAN_CD  FROM  GIBU.TBRA_REPT_TRANS  A  ,  GIBU.TBRA_REPT_DISTR  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_DEPT  D  WHERE  B.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  A.BRAN_CD,  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  C.GIBU  =  B.BRAN_CD   \n";
        query +=" AND  D.GIBU  =  C.GIBU ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �й� ���� ��ȸ �������
    // ���� �й� ���� ��ȸ �������
    public DOBJ CALLrept_distr_select_MRG2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL2, SEL15","");
        rvobj.setName("MRG2") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ���� �й� ���� ��ȸ
    // �Ա����ڸ� �������� ���� �й� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_distr_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("SEL2").firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.DISTR_GBN  ,  A.DISTR_SEQ  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  C.UPSO_NM  ,  DECODE(B.START_YRMN,  NULL,  DECODE(D.NOTE_YRMN,  NULL,  '',  TO_CHAR(ADD_MONTHS(TO_DATE(D.NOTE_YRMN,  'YYYYMM'),  1),  'YYYYMM')  ||  '01'),  B.START_YRMN)  START_YRMN  ,  B.END_YRMN  ,  A.DISTR_AMT  ,  D.NOTE_YRMN  ,  A.MAPPING_DAY  ,  A.REMAK  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(MIN(NOTE_YRMN),  NULL,  NULL,  MIN(NOTE_YRMN)  ||  '01')  START_YRMN  ,  DECODE(MAX(NOTE_YRMN),  NULL,  NULL,  MAX(NOTE_YRMN)  ||  '01')  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  B  ,  GIBU.TBRA_UPSO  C  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  ,  MAX(NOTE_YRMN)  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  )  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  D  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  B.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  B.DISTR_SEQ(+)  =  A.DISTR_SEQ   \n";
        query +=" AND  D.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  D.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  D.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  D.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  D.DISTR_SEQ(+)  =  A.DISTR_SEQ  ORDER  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN,  A.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �й� ���� ��ȸ ����
    public DOBJ CALLrept_distr_select_MRG7(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG7");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL3, SEL6","");
        rvobj.setName("MRG7") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ���� �й� ���� ��ȸ
    // �Ա����ڸ� �������� ���� �й� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_distr_select_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_select_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_select_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.DISTR_GBN  ,  A.DISTR_SEQ  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  C.UPSO_NM  ,  DECODE(B.START_YRMN,  NULL,  DECODE(D.NOTE_YRMN,  NULL,'',  TO_CHAR(ADD_MONTHS(TO_DATE(D.NOTE_YRMN,  'YYYYMM'),  1),  'YYYYMM')  ||  '01'),  B.START_YRMN)  START_YRMN  ,  B.END_YRMN  ,  A.DISTR_AMT  ,  D.NOTE_YRMN  ,  A.MAPPING_DAY  ,  A.REMAK  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(MIN(NOTE_YRMN),  NULL,  NULL,  MIN(NOTE_YRMN)  ||  '01')  START_YRMN  ,  DECODE(MAX(NOTE_YRMN),  NULL,  NULL,  MAX(NOTE_YRMN)  ||  '01')  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  B  ,  GIBU.TBRA_UPSO  C  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  ,  MAX(NOTE_YRMN)  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  )  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  D  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  B.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  B.DISTR_SEQ(+)  =  A.DISTR_SEQ   \n";
        query +=" AND  D.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  D.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  D.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  D.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  D.DISTR_SEQ(+)  =  A.DISTR_SEQ  ORDER  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN,  A.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �й� ���� ��ȸ
    // ���ο� �й�� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_distr_select_SEL15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL15");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL15");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_select_SEL15(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL15");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_select_SEL15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.REPTPRES  ,  B.DISTR_AMT  ,  A.BALANCE  ,  A.RECV_DAY  ,  A.RECEPT_BANK  ,  B.BRAN_CD  RECV_BRAN_CD  ,  D.DEPT_NM  RECV_BRAN_NM  ,  C.DEPT_NM  BRAN_NM  ,  A.BRAN_CD  ,  B.REMAK  ,  A.INSPRES_ID  ,  A.INS_DATE  ,  '03'  REPT_GBN  ,  :BRAN_CD  S_BRAN_CD  FROM  GIBU.TBRA_REPT_TRANS  A  ,  GIBU.TBRA_REPT_DISTR  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_DEPT  D  WHERE  B.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  A.BRAN_CD,  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  C.GIBU  =  B.BRAN_CD   \n";
        query +=" AND  D.GIBU  =  C.GIBU ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_distr_select
    //##**$$rept_bankbook_insert_BAK
    /* * ���α׷��� : bra04_s06
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/25
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_bankbook_insert_BAK(DOBJ dobj)
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
            dobj  = CALLrept_bankbook_insert_BAK_MPD48(Conn, dobj);           //  �ο� ���� ó��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLrept_bankbook_insert_BAK_SEL4(Conn, dobj);           //  ������ �Ա� ���� ��ȸ
            dobj  = CALLrept_bankbook_insert_BAK_SEL39(Conn, dobj);           //  �������� ��ȸ
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
    public DOBJ CTLrept_bankbook_insert_BAK( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_bankbook_insert_BAK_MPD48(Conn, dobj);           //  �ο� ���� ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLrept_bankbook_insert_BAK_SEL4(Conn, dobj);           //  ������ �Ա� ���� ��ȸ
        dobj  = CALLrept_bankbook_insert_BAK_SEL39(Conn, dobj);           //  �������� ��ȸ
        return dobj;
    }
    // �ο� ���� ó��
    public DOBJ CALLrept_bankbook_insert_BAK_MPD48(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD48");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("CRUD").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_bankbook_insert_BAK_SEL1(Conn, dobj);           //  ����� ����Ȯ��
                if( dobj.getRetObject("SEL1").getRecord().getDouble("ACCU_CNT") == 0)
                {
                    dobj  = CALLrept_bankbook_insert_BAK_OSP1(Conn, dobj);           //  ������ �Ա� ó��
                    if(!dobj.getRetObject("OSP1").getRecord().get("CNT_INST").equals("1"))
                    {
                        ////
                        String UPSO_CD="";   //���� �ڵ�
                        if( dobj.getGVString ("UPSO_CD").equals(""))
                        {
                            UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");
                        }
                        else
                        {
                            UPSO_CD = dobj.getGVString("UPSO_CD")+", "+dobj.getRetObject("R").getRecord().get("UPSO_CD");
                        }
                        dobj.setGVValue("UPSO_CD",UPSO_CD);
                    }
                }
                else
                {
                    dobj  = CALLrept_bankbook_insert_BAK_OSP63(Conn, dobj);           //  ������ �Ա� ó��
                }
            }
            else if( dvobj.getRecord().get("CRUD").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_bankbook_insert_BAK_SEL31(Conn, dobj);           //  ���� �ڵ� Ȯ��
                if(!dobj.getRetObject("R").getRecord().get("UPSO_CD").equals("") && !dobj.getRetObject("R").getRecord().get("UPSO_CD").equals(dobj.getRetObject("SEL31").getRecord().get("UPSO_CD")))
                {
                    dobj  = CALLrept_bankbook_insert_BAK_SEL45(Conn, dobj);           //  �Ա� ���� Ȯ��
                    if( dobj.getRetObject("SEL45").getRecord().getDouble("REPT_CNT") == 0)
                    {
                        dobj  = CALLrept_bankbook_insert_BAK_DEL47(Conn, dobj);           //  ���� ���� ����
                        dobj  = CALLrept_bankbook_insert_BAK_DEL48(Conn, dobj);           //  �Ա� û�� ���� ����
                        dobj  = CALLrept_bankbook_insert_BAK_DEL79(Conn, dobj);           //  �ܰ��� ����
                        dobj  = CALLrept_bankbook_insert_BAK_UPD50(Conn, dobj);           //  �Ա� ���� ����
                        dobj  = CALLrept_bankbook_insert_BAK_UPD51(Conn, dobj);           //  �Ա� ���� ����
                        dobj  = CALLrept_bankbook_insert_BAK_SEL52(Conn, dobj);           //  ���� �Ա����� ��ȸ
                        if( dobj.getRetObject("SEL52").getRecord().getDouble("CNT") == 0)
                        {
                            dobj  = CALLrept_bankbook_insert_BAK_UPD54(Conn, dobj);           //  ���� �ű��� ����
                            dobj  = CALLrept_bankbook_insert_BAK_SEL55(Conn, dobj);           //  ����� ����Ȯ��
                            if( dobj.getRetObject("SEL55").getRecord().getDouble("ACCU_CNT") == 0)
                            {
                                dobj  = CALLrept_bankbook_insert_BAK_OSP57(Conn, dobj);           //  ������ �Ա� ó��
                                if(!dobj.getRetObject("OSP57").getRecord().get("CNT_INST").equals("1"))
                                {
                                    ////
                                    String UPSO_CD="";   //���� �ڵ�
                                    if( dobj.getGVString ("UPSO_CD").equals(""))
                                    {
                                        UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");
                                    }
                                    else
                                    {
                                        UPSO_CD = dobj.getGVString("UPSO_CD")+", "+dobj.getRetObject("R").getRecord().get("UPSO_CD");
                                    }
                                    dobj.setGVValue("UPSO_CD",UPSO_CD);
                                }
                            }
                            else
                            {
                                dobj  = CALLrept_bankbook_insert_BAK_OSP64(Conn, dobj);           //  ������ �Ա� ó��
                            }
                        }
                    }
                    else
                    {
                        dobj  = CALLrept_bankbook_insert_BAK_DEL59(Conn, dobj);           //  �������� �ʱ�ȭ
                        dobj  = CALLrept_bankbook_insert_BAK_XIUD60(Conn, dobj);           //  �������� ����
                    }
                }
            }
            else if( dvobj.getRecord().get("CRUD").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_bankbook_insert_BAK_SEL34(Conn, dobj);           //  �Ա� ���� Ȯ��
                if( dobj.getRetObject("SEL34").getRecord().getDouble("REPT_CNT") == 0)
                {
                    dobj  = CALLrept_bankbook_insert_BAK_DEL36(Conn, dobj);           //  ���� ���� ����
                    dobj  = CALLrept_bankbook_insert_BAK_DEL37(Conn, dobj);           //  �Ա� û�� ���� ����
                    dobj  = CALLrept_bankbook_insert_BAK_DEL78(Conn, dobj);           //  �ܰ��� ����
                    dobj  = CALLrept_bankbook_insert_BAK_UPD38(Conn, dobj);           //  �Ա� ���� ����
                    dobj  = CALLrept_bankbook_insert_BAK_UPD39(Conn, dobj);           //  �Ա� ���� ����
                    dobj  = CALLrept_bankbook_insert_BAK_SEL40(Conn, dobj);           //  ���� �Ա����� ��ȸ
                    if( dobj.getRetObject("SEL40").getRecord().getDouble("CNT") == 0)
                    {
                        dobj  = CALLrept_bankbook_insert_BAK_UPD42(Conn, dobj);           //  ���� �ű��� ����
                    }
                }
                else
                {
                    dobj  = CALLrept_bankbook_insert_BAK_DEL61(Conn, dobj);           //  �������� �ʱ�ȭ
                    dobj  = CALLrept_bankbook_insert_BAK_XIUD62(Conn, dobj);           //  �������� ����
                }
            }
        }
        return dobj;
    }
    // ����� ����Ȯ��
    // ������� ��ü���� Ȯ���Ѵ�
    public DOBJ CALLrept_bankbook_insert_BAK_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_BAK_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL   \n";
        query +=" AND  JUDG_CD  ='1' ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ա� ���� Ȯ��
    // �ش� �Ա��� ���ķ� �Աݵ� ������ �ִ��� Ȯ���Ѵ�.  �Ա� ������ �ִ� ��� ������ �� ����
    public DOBJ CALLrept_bankbook_insert_BAK_SEL34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL34");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL34");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_BAK_SEL34(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL34");
        rvobj.Println("SEL34");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_SEL34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(UPSO_CD)  REPT_CNT  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  (REPT_DAY  ||  REPT_NUM)  >  (:REPT_DAY  ||  :REPT_NUM) ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �ڵ� Ȯ��
    public DOBJ CALLrept_bankbook_insert_BAK_SEL31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL31");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL31");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_BAK_SEL31(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL31");
        rvobj.Println("SEL31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_SEL31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   DISRT_SEQ = dobj.getRetObject("R").getRecord().get("DISRT_SEQ");   //DISRT_SEQ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  DISTR_SEQ  =  :DISRT_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("DISRT_SEQ", DISRT_SEQ);               //DISRT_SEQ
        return sobj;
    }
    // ������ �Ա� ó��
    // ������ �Ա� ó��
    public DOBJ CALLrept_bankbook_insert_BAK_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP1");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","UPSO_CD","START_YRMN","END_YRMN","REPT_AMT","COMIS","DISTR_GBN","REMAK","INSPRES_ID"};
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
        String   spname ="GIBU.SP_PROC_REPT_TRANS";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
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
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �Ա� ���� Ȯ��
    // �ش� �Ա��� ���ķ� �Աݵ� ������ �ִ��� Ȯ���Ѵ�.  �Ա� ������ �ִ� ��� ������ �� ����
    public DOBJ CALLrept_bankbook_insert_BAK_SEL45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL45");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL45");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_BAK_SEL45(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL45");
        rvobj.Println("SEL45");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_SEL45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(UPSO_CD)  REPT_CNT  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  (REPT_DAY  ||  REPT_NUM)  >  (:REPT_DAY  ||  :REPT_NUM) ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� ���� ����
    // ���� ���� ����
    public DOBJ CALLrept_bankbook_insert_BAK_DEL36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL36");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL36");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_DEL36(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL36") ;
        rvobj.Println("DEL36");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_DEL36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NOTE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �Ա� û�� ���� ����
    // �Ա� û�� ���� ����
    public DOBJ CALLrept_bankbook_insert_BAK_DEL37(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL37");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL37");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_DEL37(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL37") ;
        rvobj.Println("DEL37");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_DEL37(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_DEMD_REPT  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �ܰ��� ����
    // �ܰ��� ����
    public DOBJ CALLrept_bankbook_insert_BAK_DEL78(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL78");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL78");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_DEL78(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL78") ;
        rvobj.Println("DEL78");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_DEL78(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_BALANCE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // ���� ���� ����
    // ���� ���� ����
    public DOBJ CALLrept_bankbook_insert_BAK_DEL47(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL47");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL47");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_DEL47(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL47") ;
        rvobj.Println("DEL47");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_DEL47(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NOTE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // ������ �Ա� ó��
    // ������ �Ա� ó��
    public DOBJ CALLrept_bankbook_insert_BAK_OSP63(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP63");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP63");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","UPSO_CD","START_YRMN","END_YRMN","REPT_AMT","COMIS","DISTR_GBN","REMAK","INSPRES_ID"};
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
        String   spname ="GIBU.SP_PROC_REPT_TRANS_ACCU";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
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
        robj.setName("OSP63");
        robj.Println("OSP63");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �Ա� ���� ����
    // �Ա� ���� ����
    public DOBJ CALLrept_bankbook_insert_BAK_UPD38(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD38");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD38");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_UPD38(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD38") ;
        rvobj.Println("UPD38");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_UPD38(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   UPSO_CD ="";   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT_TRANS  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ա� û�� ���� ����
    // �Ա� û�� ���� ����
    public DOBJ CALLrept_bankbook_insert_BAK_DEL48(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL48");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL48");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_DEL48(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL48") ;
        rvobj.Println("DEL48");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_DEL48(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_DEMD_REPT  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �ܰ��� ����
    // �ܰ��� ����
    public DOBJ CALLrept_bankbook_insert_BAK_DEL79(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL79");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL79");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_DEL79(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL79") ;
        rvobj.Println("DEL79");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_DEL79(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_BALANCE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �Ա� ���� ����
    // �Ա� ���� ����
    public DOBJ CALLrept_bankbook_insert_BAK_UPD39(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD39");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD39");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_UPD39(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD39") ;
        rvobj.Println("UPD39");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_UPD39(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   REPT_GBN ="03";   //�Ա� ����
        String   UPSO_CD ="";   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ա� ���� ����
    // �Ա� ���� ����
    public DOBJ CALLrept_bankbook_insert_BAK_UPD50(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD50");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD50");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_UPD50(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD50") ;
        rvobj.Println("UPD50");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_UPD50(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   UPSO_CD ="";   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT_TRANS  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �Ա����� ��ȸ
    public DOBJ CALLrept_bankbook_insert_BAK_SEL40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL40");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL40");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_BAK_SEL40(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL40");
        rvobj.Println("SEL40");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_SEL40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(UPSO_CD)  CNT  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ա� ���� ����
    // �Ա� ���� ����
    public DOBJ CALLrept_bankbook_insert_BAK_UPD51(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD51");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD51");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_UPD51(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD51") ;
        rvobj.Println("UPD51");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_UPD51(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   REPT_GBN ="03";   //�Ա� ����
        String   UPSO_CD ="";   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �Ա����� ��ȸ
    public DOBJ CALLrept_bankbook_insert_BAK_SEL52(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL52");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL52");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_BAK_SEL52(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL52");
        rvobj.Println("SEL52");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_SEL52(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(UPSO_CD)  CNT  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �ű��� ����
    // ���� �ű��� ����
    public DOBJ CALLrept_bankbook_insert_BAK_UPD42(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD42");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD42");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_UPD42(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD42") ;
        rvobj.Println("UPD42");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_UPD42(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   NEW_DAY ="";   //�ű� ����
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , NEW_DAY=:NEW_DAY , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("NEW_DAY", NEW_DAY);               //�ű� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������� �ʱ�ȭ
    // �������� �ʱ�ȭ
    public DOBJ CALLrept_bankbook_insert_BAK_DEL61(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL61");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL61");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_DEL61(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL61") ;
        rvobj.Println("DEL61");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_DEL61(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_ERR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // ���� �ű��� ����
    // ���� �ű��� ����
    public DOBJ CALLrept_bankbook_insert_BAK_UPD54(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD54");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD54");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_UPD54(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD54") ;
        rvobj.Println("UPD54");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_UPD54(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   NEW_DAY ="";   //�ű� ����
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , NEW_DAY=:NEW_DAY , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("NEW_DAY", NEW_DAY);               //�ű� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������� ����
    public DOBJ CALLrept_bankbook_insert_BAK_XIUD62(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD62");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD62");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_XIUD62(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD62");
        rvobj.Println("XIUD62");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_XIUD62(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   COMIS = dobj.getRetObject("R").getRecord().getDouble("COMIS");   //������
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   REPT_AMT = dobj.getRetObject("R").getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   START_YRMN = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_REPT_ERR ( REPT_DAY , REPT_NUM , REPT_GBN , UPSO_CD , START_YRMN , END_YRMN , REPT_AMT , COMIS , RECV_DAY , BANK_CD , ACCN_NUM , BRAN_CD , ERR_GBN , ERR_CTENT , INSPRES_ID , INS_DATE) SELECT REPT_DAY , REPT_NUM , REPT_GBN , :UPSO_CD , :START_YRMN , :END_YRMN , :REPT_AMT , :COMIS , RECV_DAY , BANK_CD , ACCN_NUM , BRAN_CD , :ERR_CODE , :ERR_MESG , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_REPT_TRANS WHERE REPT_DAY = :REPT_DAY AND REPT_NUM = :REPT_NUM";
        sobj.setSql(query);
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ����� ����Ȯ��
    // ������� ��ü���� Ȯ���Ѵ�
    public DOBJ CALLrept_bankbook_insert_BAK_SEL55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL55");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL55");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_BAK_SEL55(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL55");
        rvobj.Println("SEL55");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_SEL55(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL   \n";
        query +=" AND  JUDG_CD  ='1' ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������ �Ա� ó��
    // ������ �Ա� ó��
    public DOBJ CALLrept_bankbook_insert_BAK_OSP57(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP57");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP57");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","UPSO_CD","START_YRMN","END_YRMN","REPT_AMT","COMIS","DISTR_GBN","REMAK","INSPRES_ID"};
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
        String   spname ="GIBU.SP_PROC_REPT_TRANS";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
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
        robj.setName("OSP57");
        robj.Println("OSP57");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ������ �Ա� ó��
    // ������ �Ա� ó��
    public DOBJ CALLrept_bankbook_insert_BAK_OSP64(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP64");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP64");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","UPSO_CD","START_YRMN","END_YRMN","REPT_AMT","COMIS","DISTR_GBN","REMAK","INSPRES_ID"};
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
        String   spname ="GIBU.SP_PROC_REPT_TRANS_ACCU";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
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
        robj.setName("OSP64");
        robj.Println("OSP64");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �������� �ʱ�ȭ
    // �������� �ʱ�ȭ
    public DOBJ CALLrept_bankbook_insert_BAK_DEL59(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL59");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL59");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_DEL59(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL59") ;
        rvobj.Println("DEL59");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_DEL59(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_ERR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �������� ����
    public DOBJ CALLrept_bankbook_insert_BAK_XIUD60(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD60");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD60");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_BAK_XIUD60(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD60");
        rvobj.Println("XIUD60");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_XIUD60(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   COMIS = dobj.getRetObject("R").getRecord().getDouble("COMIS");   //������
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   REPT_AMT = dobj.getRetObject("R").getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   START_YRMN = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   UPSO_CD = dobj.getRetObject("SEL31").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_REPT_ERR ( REPT_DAY , REPT_NUM , REPT_GBN , UPSO_CD , START_YRMN , END_YRMN , REPT_AMT , COMIS , RECV_DAY , BANK_CD , ACCN_NUM , BRAN_CD , ERR_GBN , ERR_CTENT , INSPRES_ID , INS_DATE) SELECT REPT_DAY , REPT_NUM , REPT_GBN , :UPSO_CD , :START_YRMN , :END_YRMN , :REPT_AMT , :COMIS , RECV_DAY , BANK_CD , ACCN_NUM , BRAN_CD , :ERR_CODE , :ERR_MESG , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_REPT_TRANS WHERE REPT_DAY = :REPT_DAY AND REPT_NUM = :REPT_NUM";
        sobj.setSql(query);
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������ �Ա� ���� ��ȸ
    // ������ �Ա� ���� ��ȸ
    public DOBJ CALLrept_bankbook_insert_BAK_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_BAK_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.REPTPRES  ,  A.REPT_AMT  ,  A.BALANCE  ,  A.RECV_DAY  ,  A.RECEPT_BANK  ,  A.BRAN_CD  ,  A.REMAK  ,  B.UPSO_CD  ,  B.START_YRMN  ,  B.END_YRMN  FROM  GIBU.TBRA_REPT_TRANS  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_NUM  ,  MIN(DEMD_YRMN)  START_YRMN  ,  MAX(DEMD_YRMN)  END_YRMN  FROM  GIBU.TBRA_DEMD_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  '03'  GROUP  BY  UPSO_CD,  REPT_NUM  )  B  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.DISTR_GBN  IS  NULL   \n";
        query +=" AND  B.REPT_NUM(+)  =  A.REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �������� ��ȸ
    public DOBJ CALLrept_bankbook_insert_BAK_SEL39(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL39");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL39");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_BAK_SEL39(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL39");
        rvobj.Println("SEL39");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_BAK_SEL39(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("S").firstRecord().get("REPT_GBN");   //�Ա� ����
        String   UPSO_CD = dobj.getGVString("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  UPSO_CD  ,  START_YRMN  ,  END_YRMN  ,  REPT_AMT  ,  COMIS  ,  RECV_DAY  ,  ACCN_NUM  ,  ERR_GBN  ,  ERR_CTENT  FROM  GIBU.TBRA_REPT_ERR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  UPSO_CD  IN  (:UPSO_CD) ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_bankbook_insert_BAK
    //##**$$get_use_amt
    /* * ���α׷��� : bra04_s06
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/27
    * ����    : �Աݱݾ����� ó�������� ���۳��, ������, û������, û���ݾ��� ����Ѵ�.
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
    //##**$$rept_upso_distr_insert
    /* * ���α׷��� : bra04_s06
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/02
    * ����    : ������ �Աݳ����� ���� ���ҿ� �й�� ������ ó���� �� ����� �����Ѵ�.
    (����/���� ó���� ������. ���� :  ���� �� �������� ������ ��)
    �������� : ������ �Աݳ����� �Աݱݾ��� �й�� �ݾ��� ���պ��� ���� ���
    �������� : �Աݳ��� ���Ŀ� ���ε� ������ ���� ���
    Input
    ACCN_NUM (���� ��ȣ)
    BALANCE (�ܾ�)
    BANK_CD (���� �ڵ�)
    BRAN_CD (���� �ڵ�)
    COMIS (������)
    DEMD_MMCNT (û��������)
    DISTR_AMT (�й� �ݾ�)
    DISTR_GBN (�Ա� �й� ����)
    DISTR_SEQ (�й� ����)
    END_YRMN (������)
    IUDFLAG (���ڵ���±���)
    NOTE_YRMN (���� ���)
    RECV_DAY (���� ����)
    REMAK (���)
    REPT_DAY (�Ա�����)
    REPT_GBN (�Ա� ����)
    REPT_NUM (�Ա� ��ȣ)
    START_YRMN (���۳��)
    UPSO_CD (���� �ڵ�)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_upso_distr_insert(DOBJ dobj)
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
            ////
            String   TEMP1 ="0";         //�ӽ��÷�1
            dobj.setGVValue("TEMP1",TEMP1);
            dobj  = CALLrept_upso_distr_insert_MIUD1(Conn, dobj);           //  �ο���� ó��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLrept_upso_distr_insert_SEL9(Conn, dobj);           //  ���� �й� ���� ��ȸ
            dobj  = CALLrept_upso_distr_insert_SEL10(Conn, dobj);           //  �������� ��ȸ
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
    public DOBJ CTLrept_upso_distr_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        ////
        String   TEMP1 ="0";         //�ӽ��÷�1
        dobj.setGVValue("TEMP1",TEMP1);
        dobj  = CALLrept_upso_distr_insert_MIUD1(Conn, dobj);           //  �ο���� ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLrept_upso_distr_insert_SEL9(Conn, dobj);           //  ���� �й� ���� ��ȸ
        dobj  = CALLrept_upso_distr_insert_SEL10(Conn, dobj);           //  �������� ��ȸ
        return dobj;
    }
    // �ο���� ó��
    public DOBJ CALLrept_upso_distr_insert_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MIUD1");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_upso_distr_insert_XIUD1(Conn, dobj);           //  �������� ����
                dobj  = CALLrept_upso_distr_insert_SEL1(Conn, dobj);           //  �Ա� ������ ����
                dobj  = CALLrept_upso_distr_insert_SEL3(Conn, dobj);           //  ���Ȯ��
                if(!dobj.getRetObject("SEL3").getRecord().get("ACCU_DAY").equals(""))
                {
                    dobj  = CALLrept_upso_distr_insert_OSP2(Conn, dobj);           //  ��� ���� �Ա�
                    ////
                    String   TEMP1 = dobj.getRetObject("OSP2").getRecord().get("CNT_INST");         //�ӽ��÷�1
                    dobj.setGVValue("TEMP1",TEMP1);
                }
                else
                {
                    dobj  = CALLrept_upso_distr_insert_OSP3(Conn, dobj);           //  ���� �Ա�
                    ////
                    String   TEMP1 = dobj.getRetObject("OSP3").getRecord().get("CNT_INST");         //�ӽ��÷�1
                    dobj.setGVValue("TEMP1",TEMP1);
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_upso_distr_insert_XIUD99(Conn, dobj);           //  �������� ����
                dobj  = CALLrept_upso_distr_insert_SEL4(Conn, dobj);           //  �Ա� ������ ����
                dobj  = CALLrept_upso_distr_insert_SEL5(Conn, dobj);           //  ���Ȯ��
                if( dobj.getRetObject("SEL5").getRecord().get("ACCU_GBN").equals("22"))
                {
                    dobj  = CALLrept_upso_distr_insert_OSP4(Conn, dobj);           //  ��� ���� �Ա�
                    ////
                    String   TEMP1 = dobj.getRetObject("OSP4").getRecord().get("CNT_INST");         //�ӽ��÷�1
                    dobj.setGVValue("TEMP1",TEMP1);
                }
                else
                {
                    dobj  = CALLrept_upso_distr_insert_OSP5(Conn, dobj);           //  ���� �Ա�
                    ////
                    String   TEMP1 = dobj.getRetObject("OSP5").getRecord().get("CNT_INST");         //�ӽ��÷�1
                    dobj.setGVValue("TEMP1",TEMP1);
                }
            }
        }
        return dobj;
    }
    // �������� ����
    public DOBJ CALLrept_upso_distr_insert_XIUD99(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD99");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_upso_distr_insert_XIUD99(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD99");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_upso_distr_insert_XIUD99(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE GIBU.TBRA_REPT_ERR  \n";
        query +=" WHERE REPT_DAY = :REPT_DAY  \n";
        query +=" AND REPT_GBN = :REPT_GBN  \n";
        query +=" AND	BRAN_CD = :BRAN_CD  \n";
        query +=" AND	INSPRES_ID = :INSPRES_ID";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �������� ����
    public DOBJ CALLrept_upso_distr_insert_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_upso_distr_insert_XIUD1(dobj, dvobj);
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
    private SQLObject SQLrept_upso_distr_insert_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE GIBU.TBRA_REPT_ERR  \n";
        query +=" WHERE REPT_DAY = :REPT_DAY  \n";
        query +=" AND REPT_GBN = :REPT_GBN  \n";
        query +=" AND	BRAN_CD = :BRAN_CD  \n";
        query +=" AND	INSPRES_ID = :INSPRES_ID";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �Ա� ������ ����
    // ���ν����� ȣ���ϱ� ���� �Աݵ����͸� �����Ѵ�
    public DOBJ CALLrept_upso_distr_insert_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_upso_distr_insert_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_upso_distr_insert_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   COMIS = dobj.getRetObject("R").getRecord().getDouble("COMIS");   //������
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");   //�Ա� �й� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   DISTR_AMT = dobj.getRetObject("R").getRecord().getDouble("DISTR_AMT");   //�й� �ݾ�
        double   DEMD_MMCNT = dobj.getRetObject("R").getRecord().getDouble("DEMD_MMCNT");   //û��������
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   START_YRMN = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   DISTR_SEQ = dobj.getRetObject("R").getRecord().get("DISTR_SEQ");   //�й� ����
        String   NOTE_YRMN = dobj.getRetObject("R").getRecord().get("NOTE_YRMN");   //���� ���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   NESS_AMT = dobj.getRetObject("R").getRecord().getDouble("NESS_AMT");   //�ʿ� �ݾ�
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        double   BALANCE = dobj.getRetObject("R").getRecord().getDouble("BALANCE");   //�ܾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :NOTE_YRMN  AS  NOTE_YRMN  ,  :DISTR_SEQ  AS  DISTR_SEQ  ,  :DISTR_GBN  AS  DISTR_GBN  ,  :BRAN_CD  AS  BRAN_CD  ,  DECODE(UPSO_CD,  NULL,  :UPSO_CD,  UPSO_CD)  UPSO_CD  ,  :DEMD_MMCNT  AS  DEMD_MMCNT  ,  SUBSTR(:START_YRMN,  1,  6)  AS  START_YRMN  ,  SUBSTR(:END_YRMN,  1,  6)  AS  END_YRMN  ,  :DISTR_AMT  AS  DISTR_AMT  ,  :COMIS  AS  COMIS  ,  :BALANCE  AS  BALANCE  ,  RECV_DAY  ,  BANK_CD  ,  ACCN_NUM  ,  NULL  AS  CLAIM_GBN  ,  :REMAK  AS  REMAK  ,  'D'  AS  CRUD  ,  :INSPRES_ID  AS  INSPRES_ID  ,  :NESS_AMT  AS  NESS_AMT  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("DISTR_AMT", DISTR_AMT);               //�й� �ݾ�
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //û��������
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //�й� ����
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //���� ���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("NESS_AMT", NESS_AMT);               //�ʿ� �ݾ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("BALANCE", BALANCE);               //�ܾ�
        return sobj;
    }
    // �Ա� ������ ����
    // ���ν����� ȣ���ϱ� ���� �Աݵ����͸� �����Ѵ�
    public DOBJ CALLrept_upso_distr_insert_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_upso_distr_insert_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_upso_distr_insert_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   COMIS = dobj.getRetObject("R").getRecord().getDouble("COMIS");   //������
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");   //�Ա� �й� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   DISTR_AMT = dobj.getRetObject("R").getRecord().getDouble("DISTR_AMT");   //�й� �ݾ�
        double   DEMD_MMCNT = dobj.getRetObject("R").getRecord().getDouble("DEMD_MMCNT");   //û��������
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   START_YRMN = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   DISTR_SEQ = dobj.getRetObject("R").getRecord().get("DISTR_SEQ");   //�й� ����
        String   NOTE_YRMN = dobj.getRetObject("R").getRecord().get("NOTE_YRMN");   //���� ���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   NESS_AMT = dobj.getRetObject("R").getRecord().getDouble("NESS_AMT");   //�ʿ� �ݾ�
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        double   BALANCE = dobj.getRetObject("R").getRecord().getDouble("BALANCE");   //�ܾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :NOTE_YRMN  AS  NOTE_YRMN  ,  :DISTR_SEQ  AS  DISTR_SEQ  ,  :DISTR_GBN  AS  DISTR_GBN  ,  :BRAN_CD  AS  BRAN_CD  ,  :UPSO_CD  AS  UPSO_CD  ,  :DEMD_MMCNT  AS  DEMD_MMCNT  ,  SUBSTR(:START_YRMN,  1,  6)  AS  START_YRMN  ,  SUBSTR(:END_YRMN,  1,  6)  AS  END_YRMN  ,  :DISTR_AMT  AS  DISTR_AMT  ,  :COMIS  AS  COMIS  ,  :BALANCE  AS  BALANCE  ,  RECV_DAY  ,  BANK_CD  ,  ACCN_NUM  ,  NULL  AS  CLAIM_GBN  ,  :REMAK  AS  REMAK  ,  'I'  AS  CRUD  ,  :INSPRES_ID  AS  INSPRES_ID  ,  :NESS_AMT  AS  NESS_AMT  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("DISTR_AMT", DISTR_AMT);               //�й� �ݾ�
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //û��������
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //�й� ����
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //���� ���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("NESS_AMT", NESS_AMT);               //�ʿ� �ݾ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("BALANCE", BALANCE);               //�ܾ�
        return sobj;
    }
    // ���Ȯ��
    public DOBJ CALLrept_upso_distr_insert_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_upso_distr_insert_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_upso_distr_insert_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���Ȯ��
    public DOBJ CALLrept_upso_distr_insert_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_upso_distr_insert_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_upso_distr_insert_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��� ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_upso_distr_insert_OSP4(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP4");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL4");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP4");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","DISTR_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
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
        String[] outcolnms={"CNT_INST"};;
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
        robj.Println("OSP4");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ��� ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_upso_distr_insert_OSP2(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP2");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL1");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP2");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","DISTR_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
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
        String[] outcolnms={"CNT_INST"};;
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
        robj.Println("OSP2");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_upso_distr_insert_OSP5(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP5");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL4");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP5");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","DISTR_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
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
        String[] outcolnms={"CNT_INST"};;
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
        robj.Println("OSP5");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_upso_distr_insert_OSP3(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP3");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL1");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP3");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","DISTR_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
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
        String[] outcolnms={"CNT_INST"};;
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
        robj.Println("OSP3");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �й� ���� ��ȸ
    // �Ա����ڸ� �������� ���� �й� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_upso_distr_insert_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_upso_distr_insert_SEL9(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        rvobj.setRetcode(1);
        rvobj.Println("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_upso_distr_insert_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.DISTR_GBN  ,  A.DISTR_SEQ  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  C.UPSO_NM  ,  DECODE(B.START_YRMN,  NULL,  TO_CHAR(ADD_MONTHS(TO_DATE(D.NOTE_YRMN,  'YYYYMM'),  1),  'YYYYMM')  ||  '01',  B.START_YRMN)  START_YRMN  ,  B.END_YRMN  ,  A.DISTR_AMT  ,  D.NOTE_YRMN  ,  A.MAPPING_DAY  ,  A.REMAK  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(MIN(NOTE_YRMN),  NULL,  NULL,  MIN(NOTE_YRMN)  ||  '01')  START_YRMN  ,  DECODE(MAX(NOTE_YRMN),  NULL,  NULL,  MAX(NOTE_YRMN)  ||  '01')  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  B  ,  GIBU.TBRA_UPSO  C  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  ,  MAX(NOTE_YRMN)  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  )  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  D  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  B.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  B.DISTR_SEQ(+)  =  A.DISTR_SEQ   \n";
        query +=" AND  D.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  D.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  D.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  D.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  D.DISTR_SEQ(+)  =  A.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �������� ��ȸ
    public DOBJ CALLrept_upso_distr_insert_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_upso_distr_insert_SEL10(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.Println("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_upso_distr_insert_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_GBN = dobj.getRetObject("S").firstRecord().get("REPT_GBN");   //�Ա� ����
        String   CNT_INST = dobj.getGVString("TEMP1");   //CNT_INST
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  UPSO_CD  ,  START_YRMN  ,  END_YRMN  ,  REPT_AMT  ,  COMIS  ,  RECV_DAY  ,  ACCN_NUM  ,  ERR_GBN  ,  ERR_CTENT  FROM  GIBU.TBRA_REPT_ERR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  AND	INSPRES_ID  =  :INSPRES_ID   \n";
        query +=" AND  '1'  =  DECODE(:CNT_INST,  1,  NULL,  '1') ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("CNT_INST", CNT_INST);               //CNT_INST
        return sobj;
    }
    //##**$$rept_upso_distr_insert
    //##**$$rept_distr_delete
    /* * ���α׷��� : bra04_s06
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/04
    * ����    : ������ �Աݳ����� ���� ���ΰ� �й賻���� �����Ѵ�.
    �������� : ���ΰ� �й�� ������ ���� ���Һй谡 �߻����� ���� ���
    Input
    BRAN_CD (���� �ڵ�)
    REPT_DAY (�Ա�����)
    REPT_NUM (�Ա� ��ȣ)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_distr_delete(DOBJ dobj)
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
            dobj  = CALLrept_distr_delete_SEL1(Conn, dobj);           //  �Ա����� Ȯ��
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLrept_distr_delete_DEL1(Conn, dobj);           //  ���� �й� ���� ����
                dobj  = CALLrept_distr_delete_SEL2(Conn, dobj);           //  ���� �й� ���� ��ȸ
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="���ε� �Ա������� �ֽ��ϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
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
    public DOBJ CTLrept_distr_delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_distr_delete_SEL1(Conn, dobj);           //  �Ա����� Ȯ��
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLrept_distr_delete_DEL1(Conn, dobj);           //  ���� �й� ���� ����
            dobj  = CALLrept_distr_delete_SEL2(Conn, dobj);           //  ���� �й� ���� ��ȸ
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="���ε� �Ա������� �ֽ��ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �Ա����� Ȯ��
    public DOBJ CALLrept_distr_delete_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_delete_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_delete_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("S").firstRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(UPSO_CD)  CNT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // ���� �й� ���� ����
    // ���� �й� ���� ����
    public DOBJ CALLrept_distr_delete_DEL1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLrept_distr_delete_DEL1(dobj, dvobj);
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
    private SQLObject SQLrept_distr_delete_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_DISTR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // ���� �й� ���� ��ȸ
    // ���� �й� ���� ��ȸ
    public DOBJ CALLrept_distr_delete_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_distr_delete_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_distr_delete_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.REPTPRES  ,  B.DISTR_AMT  ,  A.BALANCE  ,  A.RECV_DAY  ,  A.RECEPT_BANK  ,  C.DEPT_NM  BRAN_NM  ,  A.BRAN_CD  ,  A.REMAK  ,  A.INSPRES_ID  ,  A.INS_DATE  FROM  GIBU.TBRA_REPT_TRANS  A  ,  GIBU.TBRA_REPT_DISTR  B  ,  INSA.TCPM_DEPT  C  WHERE  B.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  A.BRAN_CD,  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  C.GIBU  =  B.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_distr_delete
    //##**$$rept_closing
    /* * ���α׷��� : bra04_s06
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/07
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
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
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
    //##**$$auto_matching
    /*
    */
    public DOBJ CTLauto_matching(DOBJ dobj)
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
            dobj  = CALLauto_matching_SEL1(Conn, dobj);           //  ����û���� ȹ��
            dobj  = CALLauto_matching_SEL2(Conn, dobj);           //  �̸��˻�
            dobj  = CALLauto_matching_MPD4(Conn, dobj);           //  �б�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLauto_matching_MRG9( dobj);        //  �����
            if( dobj.getRetObject("MRG9").getRecordCnt() == 1)
            {
                dobj  = CALLauto_matching_SEL11(Conn, dobj);           //  ����
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
    public DOBJ CTLauto_matching( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_matching_SEL1(Conn, dobj);           //  ����û���� ȹ��
        dobj  = CALLauto_matching_SEL2(Conn, dobj);           //  �̸��˻�
        dobj  = CALLauto_matching_MPD4(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLauto_matching_MRG9( dobj);        //  �����
        if( dobj.getRetObject("MRG9").getRecordCnt() == 1)
        {
            dobj  = CALLauto_matching_SEL11(Conn, dobj);           //  ����
        }
        return dobj;
    }
    // ����û���� ȹ��
    public DOBJ CALLauto_matching_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_matching_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_matching_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR ";
        sobj.setSql(query);
        return sobj;
    }
    // �̸��˻�
    public DOBJ CALLauto_matching_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_matching_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_matching_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLauto_matching_MPD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD4");
        VOBJ dvobj = dobj.getRetObject("SEL2");         //�̸��˻����� ������Ų OBJECT�Դϴ�.(CALLauto_matching_SEL2)
        dvobj.Println("MPD4");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_matching_SEL6(Conn, dobj);           //  ��ȸ
                dobj  = CALLauto_matching_ADD7( dobj);        //  �����
            }
        }
        return dobj;
    }
    // ��ȸ
    public DOBJ CALLauto_matching_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_matching_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_matching_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // �����
    public DOBJ CALLauto_matching_ADD7(DOBJ dobj) throws Exception
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
    // �����
    public DOBJ CALLauto_matching_MRG9(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG9");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"ADD7","");
        rvobj.setName("MRG9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ����
    public DOBJ CALLauto_matching_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_matching_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.Println("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_matching_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
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
    //##**$$auto_matching
    //##**$$chk_return_yn
    /*
    */
    public DOBJ CTLchk_return_yn(DOBJ dobj)
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
            dobj  = CALLchk_return_yn_SEL1(Conn, dobj);           //  ��ȯ������ȸ
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
    public DOBJ CTLchk_return_yn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLchk_return_yn_SEL1(Conn, dobj);           //  ��ȯ������ȸ
        return dobj;
    }
    // ��ȯ������ȸ
    public DOBJ CALLchk_return_yn_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLchk_return_yn_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_return_yn_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_REPT_RETURN_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$chk_return_yn
    //##**$$end
}
