
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra09_p09_4
{
    public bra09_p09_4()
    {
    }
    //##**$$staff_collect_list_al
    /*
    */
    public DOBJ CTLstaff_collect_list_al(DOBJ dobj)
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
                dobj  = CALLstaff_collect_list_al_SEL2(Conn, dobj);           //  ��纰 �հ�(����)
                dobj  = CALLstaff_collect_list_al_MRG2( dobj);        //  ��纰�հ�������
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLstaff_collect_list_al_SEL7(Conn, dobj);           //  ��纰 �հ�(����)
                dobj  = CALLstaff_collect_list_al_MRG2( dobj);        //  ��纰�հ�������
            }
            else
            {
                dobj  = CALLstaff_collect_list_al_SEL9(Conn, dobj);           //  ��纰 �հ�(��ü)
                dobj  = CALLstaff_collect_list_al_MRG2( dobj);        //  ��纰�հ�������
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
    public DOBJ CTLstaff_collect_list_al( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLstaff_collect_list_al_SEL2(Conn, dobj);           //  ��纰 �հ�(����)
            dobj  = CALLstaff_collect_list_al_MRG2( dobj);        //  ��纰�հ�������
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLstaff_collect_list_al_SEL7(Conn, dobj);           //  ��纰 �հ�(����)
            dobj  = CALLstaff_collect_list_al_MRG2( dobj);        //  ��纰�հ�������
        }
        else
        {
            dobj  = CALLstaff_collect_list_al_SEL9(Conn, dobj);           //  ��纰 �հ�(��ü)
            dobj  = CALLstaff_collect_list_al_MRG2( dobj);        //  ��纰�հ�������
        }
        return dobj;
    }
    // ��纰 �հ�(����)
    public DOBJ CALLstaff_collect_list_al_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstaff_collect_list_al_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_collect_list_al_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '1060%'   \n";
        query +=" AND  GIBU  =  BRAN_CD)  AS  BRAN_NM  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  SUM(USE_AMT)  COL_AMT  ,  SUM(DECODE(GBN,  'COL_UPSO',1,0))  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  0  AMT  ,  SUM(NVL(B.REPT_AMT,0)-NVL(B.COMIS,0))  USE_AMT  ,  MAX(C.STAFF_NUM)  STAFF_CD  ,  MAX(C.HAN_NM)  STAFF_NM  ,  0  NONPY_AMT  ,  'COL_UPSO'  GBN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  ,  INSA.TINS_MST01  C  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NEW_DAY  <=  :  YRMN||'31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.STAFF_NUM(+)  =  A.STAFF_CD  GROUP  BY  A.BRAN_CD,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XA.NEW_DAY  <=  :YRMN||'31'   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  STAFF_NM  ORDER  BY  BRAN_CD,  STAFF_NM ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ��纰�հ�������
    public DOBJ CALLstaff_collect_list_al_MRG2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL2, SEL7, SEL9","BRAN_CD, BRAN_NM, MON_AMT, UPSO_CNT, NONPY_AMT, NONPY_CNT, COL_AMT, COL_UPSO_CNT,  STAFF_NM, STAFF_AMT");
        rvobj.setName("MRG2") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ��纰 �հ�(����)
    public DOBJ CALLstaff_collect_list_al_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstaff_collect_list_al_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_collect_list_al_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '1060%'   \n";
        query +=" AND  GIBU  =  BRAN_CD)  AS  BRAN_NM  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  0  COL_AMT  ,  0  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  FROM  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  MAX(XC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(XD.STAFF_NUM)  STAFF_CD  ,  MAX(XD.HAN_NM)  STAFF_NM  ,  SUM(NVL(XB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  (XA.NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(XA.NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.DEMD_YRMN(+)  <=  :YRMN   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  GROUP  BY  XA.BRAN_CD,  XA.UPSO_CD  )  GROUP  BY  BRAN_CD,  STAFF_NM  ORDER  BY  BRAN_CD,  STAFF_NM ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ��纰 �հ�(��ü)
    public DOBJ CALLstaff_collect_list_al_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLstaff_collect_list_al_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_collect_list_al_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '1060%'   \n";
        query +=" AND  GIBU  =  BRAN_CD)  AS  BRAN_NM  ,  SUM(AMT)  MON_AMT  ,  SUM(DECODE(GBN,  'UPSO',1,0))  UPSO_CNT  ,  SUM(NONPY_AMT)  NONPY_AMT  ,  SUM(DECODE(NONPY_AMT,0,0,1))  NONPY_CNT  ,  SUM(USE_AMT)  COL_AMT  ,  SUM(DECODE(GBN,  'COL_UPSO',1,0))  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(USE_AMT)  STAFF_AMT  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  0  AMT  ,  SUM(NVL(B.REPT_AMT,0)-NVL(B.COMIS,0))  USE_AMT  ,  MAX(C.STAFF_NUM)  STAFF_CD  ,  MAX(C.HAN_NM)  STAFF_NM  ,  0  NONPY_AMT  ,  'COL_UPSO'  GBN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  ,  INSA.TINS_MST01  C  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  (A.NEW_DAY  IS  NULL   \n";
        query +=" OR  A.NEW_DAY  <=  :YRMN||'31')   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.STAFF_NUM(+)  =  A.STAFF_CD  GROUP  BY  A.BRAN_CD,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  TA.BRAN_CD  ,  TA.UPSO_CD  ,  MAX(TC.MONPRNCFEE)  AMT  ,  0  USE_AMT  ,  MAX(TD.STAFF_NUM)  STAFF_CD  ,  MAX(TD.HAN_NM)  STAFF_NM  ,  SUM(NVL(TB.TOT_DEMD_AMT,0))  NONPY_AMT  ,  'UPSO'  GBN  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  ,  TOT_USE_AMT+TOT_ADDT_AMT  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  NEW_DAY  <=  :YRMN  ||  '31')   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  TB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TINS_MST01  TD  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  (TA.NEW_DAY  IS  NULL   \n";
        query +=" OR  TA.NEW_DAY  <=  :YRMN||'31')   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  TB.UPSO_CD(+)  =  TA.UPSO_CD   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TB.DEMD_YRMN(+)  <  =:YRMN   \n";
        query +=" AND  TD.STAFF_NUM(+)  =  TA.STAFF_CD  GROUP  BY  TA.BRAN_CD,  TA.UPSO_CD  )  GROUP  BY  BRAN_CD,  STAFF_NM  ORDER  BY  BRAN_CD,  STAFF_NM ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$staff_collect_list_al
    //##**$$end
}
