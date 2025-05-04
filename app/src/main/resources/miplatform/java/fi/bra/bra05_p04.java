
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_p04
{
    public bra05_p04()
    {
    }
    //##**$$goso_search
    /* * ���α׷��� : bra05_p04
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/08
    * ���� :
    ����Ƿڳ�����&����ذ᳻������ GBN�� �°� �б��ؼ� ��ȸ�Ѵ�.
    GBN :1 �� ��� ����Ƿڳ������� ��ȸ�Ѵ�.
    GBN: 2 �ϰ��� ����ذ᳻������ ��ȸ�Ѵ�.
    1. NLL.NLL4 : ���μ������������� S ���� �ٷ� �б�(���Ǵޱ�)�� �������� �ʱ� ������ ���ǿ� ���� �бⰡ���ϵ��� ����
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLgoso_search(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj  = CALLgoso_search_SEL1(Conn, dobj);           //  ����Ƿڳ�����
                dobj  = CALLgoso_search_SEL11(Conn, dobj);           //  ���/���������(�Ƿ�)
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("3"))
            {
                dobj  = CALLgoso_search_SEL8(Conn, dobj);           //  ������೻��
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLgoso_search_SEL2(Conn, dobj);           //  ����ذ᳻����
                dobj  = CALLgoso_search_SEL12(Conn, dobj);           //  ���/���������(�ذ�)
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
    public DOBJ CTLgoso_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLgoso_search_SEL1(Conn, dobj);           //  ����Ƿڳ�����
            dobj  = CALLgoso_search_SEL11(Conn, dobj);           //  ���/���������(�Ƿ�)
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("3"))
        {
            dobj  = CALLgoso_search_SEL8(Conn, dobj);           //  ������೻��
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLgoso_search_SEL2(Conn, dobj);           //  ����ذ᳻����
            dobj  = CALLgoso_search_SEL12(Conn, dobj);           //  ���/���������(�ذ�)
        }
        return dobj;
    }
    // ����Ƿڳ�����
    public DOBJ CALLgoso_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLgoso_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgoso_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.ACCU_DAY  ,  XA.UPSO_CD  ,  XC.GRADNM  ,  XC.MONPRNCFEE  ,  XB.UPSO_NM  ,  XB.MNGEMSTR_NM  ,  XB.UPSO_PHON  ,  DECODE(XA.START_YRMN,  NULL,  '',  SUBSTR(XA.START_YRMN,0,4)||'/'||SUBSTR(XA.START_YRMN,5,2)||'  ~  '||SUBSTR(XA.END_YRMN,0,4)||'/'||SUBSTR(XA.END_YRMN,5,2))  YRMN  ,  XA.REQ_ORG_AMT  +  XA.REQ_ADDT_AMT  TOT_AMT  ,  XA.STAFF_CD  ,  XD.HAN_NM  STAFF_NM  ,  DECODE(XA.ACCU_GBN,'B','�λ�','')  GBN  FROM  GIBU.TBRA_ACCU  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  SUBSTR(XA.ACCU_DAY,  1,  6)  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XB.STAFF_CD  ORDER  BY  XA.ACCU_DAY,  XA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // ���/���������(�Ƿ�)
    public DOBJ CALLgoso_search_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLgoso_search_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgoso_search_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(XD.HAN_NM)  AS  CNT  ,  SUM(NVL(XA.REQ_ORG_AMT,  0)  +  NVL(XA.REQ_ADDT_AMT,  0))  AS  TOT_AMT  ,  XD.HAN_NM  AS  NAME  ,  'S'  AS  GBN  FROM  GIBU.TBRA_ACCU  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  AS  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  SUBSTR(XA.ACCU_DAY,  1,  6)  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XB.STAFF_CD  GROUP  BY  XD.HAN_NM  UNION  ALL   \n";
        query +=" SELECT  COUNT(XC.BSTYP_CD)  AS  CNT  ,  SUM(NVL(XA.REQ_ORG_AMT,  0)  +  NVL(XA.REQ_ADDT_AMT,  0))  AS  TOT_AMT  ,  XE.GRADNM  AS  NAME  ,  'B'  AS  GBN  FROM  GIBU.TBRA_ACCU  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  AS  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  ,  GIBU.TBRA_BSTYPGRAD  XE  WHERE  XB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  SUBSTR(XA.ACCU_DAY,  1,  6)  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XB.STAFF_CD   \n";
        query +=" AND  XE.BSTYP_CD  =  'z'   \n";
        query +=" AND  XE.GRAD_GBN  =  XC.BSTYP_CD  GROUP  BY  XC.BSTYP_CD,  XE.GRADNM ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // ������೻��
    public DOBJ CALLgoso_search_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLgoso_search_SEL8(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgoso_search_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MEMO_DAY,  C.UPSO_CD,  C.UPSO_NM,  B.PLCST_CD,   \n";
        query +=" (SELECT  BIGO  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00214'   \n";
        query +=" AND  CODE_CD  =  B.PLCST_CD   \n";
        query +=" AND  USE_YN  =  'Y')  PLSCT_NM,  A.MEMO,  A.ACCU_DAY,  A.ACCU_NUM,  A.ACCU_BRAN,  A.SNUM,A.MEMO_NUM,  A.INSPRES_ID,  A.INS_DATE,  A.MODPRES_ID,  A.MOD_DATE  FROM  GIBU.TBRA_ACCU_MEMO  A  ,  GIBU.TBRA_ACCU  B  ,  GIBU.TBRA_UPSO  C  WHERE  SUBSTR(A.MEMO_DAY,  1,  6)  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  A.ACCU_DAY  =  B.ACCU_DAY   \n";
        query +=" AND  A.ACCU_NUM  =  B.ACCU_NUM   \n";
        query +=" AND  A.ACCU_BRAN  =  B.ACCU_BRAN   \n";
        query +=" AND  B.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  C.BRAN_CD  =  :BRAN_CD  ORDER  BY  A.MEMO_DAY,  C.UPSO_CD,  B.PLCST_CD,  A.MEMO_NUM ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // ����ذ᳻����
    public DOBJ CALLgoso_search_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLgoso_search_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgoso_search_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.COMPN_DAY  ,  XA.ACCU_DAY  ,  XA.UPSO_CD  ,  XC.GRADNM  ,  XC.MONPRNCFEE  ,  XB.UPSO_NM  ,  XB.MNGEMSTR_NM  ,  XB.UPSO_PHON  ,  DECODE(XA.SOL_START_YRMN,  NULL,  '',  SUBSTR(XA.SOL_START_YRMN,0,4)||'/'||SUBSTR(XA.SOL_START_YRMN,5,2)||'~'||SUBSTR(XA.SOL_END_YRMN,0,4)||'/'||SUBSTR(XA.SOL_END_YRMN,5,2))  SOL_YRMN  ,  XA.SOL_ORG_AMT  +  XA.SOL_ADDT_AMT  SOL_TOT_AMT  ,  XA.SOL_ADDT_AMT  ,  DECODE(XA.START_YRMN,  NULL,  '',  SUBSTR(XA.START_YRMN,0,4)||'/'||SUBSTR(XA.START_YRMN,5,2)||'~'||SUBSTR(XA.END_YRMN,0,4)||'/'||SUBSTR(XA.END_YRMN,5,2))  YRMN  ,  XD.HAN_NM  STAFF_NM  ,  (CASE  WHEN  XB.CLSBS_YRMN  IS  NOT  NULL  THEN  '���  '  ELSE  '  '  END)  QUIT  ,  (CASE  WHEN  XA.JUDG_CD  =  '1'  THEN  '.����'  WHEN  XA.JUDG_CD  =  '2'  THEN  '.����'  WHEN  XA.JUDG_CD  =  '3'  THEN  '.����'  WHEN  XA.JUDG_CD  =  '4'  THEN  '.�����'  WHEN  XA.JUDG_CD  =  '5'  THEN  '.����'  WHEN  XA.JUDG_CD  =  '6'  THEN  '.����'  WHEN  XA.JUDG_CD  =  '7'  THEN  '.����'  WHEN  XA.JUDG_CD  =  '8'  THEN  '.�빫'  WHEN  XA.JUDG_CD  =  '9'  THEN  '.���'  WHEN  XA.JUDG_CD  =  '10'  THEN  '.����'  WHEN  XA.JUDG_CD  =  '11'  THEN  '.�˾�'  WHEN  XA.JUDG_CD  =  '12'  THEN  '.����'  WHEN  XA.JUDG_CD  =  '13'  THEN  '.����'  WHEN  XA.JUDG_CD  =  '14'  THEN  '.ó����'  WHEN  XA.JUDG_CD  =  '41'  THEN  '.������'  WHEN  XA.JUDG_CD  =  '99'  THEN  '.����'  END)  REMAK  ,  DECODE(XA.ACCU_GBN,'B','�λ�','')  GBN  FROM  GIBU.TBRA_ACCU  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  SUBSTR(XA.COMPN_DAY,  1,  6)  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  XA.JUDG_CD  <>  '2'   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XB.STAFF_CD  ORDER  BY  XA.COMPN_DAY,  XA.ACCU_DAY,  XA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // ���/���������(�ذ�)
    public DOBJ CALLgoso_search_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLgoso_search_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL12");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgoso_search_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(XD.HAN_NM)  AS  CNT  ,  SUM(NVL(XA.SOL_ORG_AMT,  0)  +  NVL(XA.SOL_ADDT_AMT,  0))  AS  TOT_AMT  ,  SUM(NVL(XA.SOL_ADDT_AMT,  0))  AS  ADDT_AMT  ,  XD.HAN_NM  AS  NAME  ,  'S'  AS  GBN  FROM  GIBU.TBRA_ACCU  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TINS_MST01  XD  WHERE  XB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  SUBSTR(XA.COMPN_DAY,  1,  6)  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  XA.JUDG_CD  <>  '2'   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XB.STAFF_CD  GROUP  BY  XD.HAN_NM  UNION  ALL   \n";
        query +=" SELECT  COUNT(XC.BSTYP_CD)  AS  CNT  ,  SUM(NVL(XA.SOL_ORG_AMT,  0)  +  NVL(XA.SOL_ADDT_AMT,  0))  AS  TOT_AMT  ,  SUM(NVL(XA.SOL_ADDT_AMT,  0))  AS  ADDT_AMT  ,  XE.GRADNM  AS  NAME  ,  'B'  AS  GBN  FROM  GIBU.TBRA_ACCU  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  ,  GIBU.TBRA_BSTYPGRAD  XE  WHERE  XB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  SUBSTR(XA.COMPN_DAY,  1,  6)  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  XA.JUDG_CD  <>  '2'   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XB.STAFF_CD   \n";
        query +=" AND  XE.BSTYP_CD  =  'z'   \n";
        query +=" AND  XE.GRAD_GBN  =  XC.BSTYP_CD  GROUP  BY  XC.BSTYP_CD,  XE.GRADNM ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$goso_search
    //##**$$end
}
