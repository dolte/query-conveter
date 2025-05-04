
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_p08_1
{
    public bra04_p08_1()
    {
    }
    //##**$$nonpy_stat
    /* * ���α׷��� : bra04_p08
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/11
    * ����    : ������ ��¡�� ��� ����Ʈ�� ��ȸ�Ѵ�.
    Input
    BRAN_CD (���� �ڵ�)
    END_CNT_MON (���� ����)
    STAFF_CD (��� �ڵ�)
    START_CNT_MON (���� ����)
    UPSO_TYPE (���� ����)
    YRMN (���)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLnonpy_stat(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("01"))
            {
                dobj  = CALLnonpy_stat_SEL4(Conn, dobj);           //  �������� ��¡�� ���
                dobj  = CALLnonpy_stat_MRG7( dobj);        //  �������
            }
            else if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("02"))
            {
                dobj  = CALLnonpy_stat_SEL5(Conn, dobj);           //  ������ ���� ��¡�� ���
                dobj  = CALLnonpy_stat_MRG7( dobj);        //  �������
            }
            else
            {
                dobj  = CALLnonpy_stat_SEL6(Conn, dobj);           //  ��ü���� ��¡�� ���
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
    public DOBJ CTLnonpy_stat( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("01"))
        {
            dobj  = CALLnonpy_stat_SEL4(Conn, dobj);           //  �������� ��¡�� ���
            dobj  = CALLnonpy_stat_MRG7( dobj);        //  �������
        }
        else if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("02"))
        {
            dobj  = CALLnonpy_stat_SEL5(Conn, dobj);           //  ������ ���� ��¡�� ���
            dobj  = CALLnonpy_stat_MRG7( dobj);        //  �������
        }
        else
        {
            dobj  = CALLnonpy_stat_SEL6(Conn, dobj);           //  ��ü���� ��¡�� ���
        }
        return dobj;
    }
    // �������� ��¡�� ���
    // �������� ������ ��¡�� ��踦 ��ȸ�Ѵ�
    public DOBJ CALLnonpy_stat_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLnonpy_stat_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_stat_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_CNT_MON = dvobj.getRecord().getDouble("START_CNT_MON");   //���� ����
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //���� ����
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ZC.GRADNM  ,  NVL(ZA.CNT,  0)  CNT  ,  ZA.MONPRNCFEE  ,  NVL(ZB.NONPY_CNT,  0)  NONPY_CNT  ,  NVL(ZB.NONPY_AMT,  0)  NONPY_AMT  ,  ZC.GRAD_GBN  FROM  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  CNT  ,  SUM(XB.MONPRNCFEE)  MONPRNCFEE  ,  XB.BSTYP_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.STAFF_CD  =  NVL(:STAFF_CD,  XA.STAFF_CD)   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XA.NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD  GROUP  BY  XB.BSTYP_CD  )  ZA  ,  (   \n";
        query +=" SELECT  COUNT(A.UPSO_CD)  NONPY_CNT  ,  SUM(B.TOT_DEMD_AMT)  NONPY_AMT  ,  A.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  12)  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.BSTYP_CD  )  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZC.BSTYP_CD  =  'z'   \n";
        query +=" AND  ZA.BSTYP_CD(+)  =  ZC.GRAD_GBN   \n";
        query +=" AND  ZB.BSTYP_CD(+)  =  ZC.GRAD_GBN   \n";
        query +=" AND  CNT  >0 ";
        sobj.setSql(query);
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �������
    public DOBJ CALLnonpy_stat_MRG7(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG7");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL4, SEL5","");
        rvobj.setName("MRG7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ������ ���� ��¡�� ���
    // �������� ������ ��¡�� ��踦 ��ȸ�Ѵ�
    public DOBJ CALLnonpy_stat_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLnonpy_stat_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_stat_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //���� ����
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //���� ����
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ZC.GRADNM  ,  NVL(ZA.CNT,  0)  CNT  ,  ZA.MONPRNCFEE  ,  NVL(ZB.NONPY_CNT,  0)  NONPY_CNT  ,  NVL(ZB.NONPY_AMT,  0)  NONPY_AMT  ,  ZC.GRAD_GBN  FROM  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  CNT  ,  SUM(XB.MONPRNCFEE)  MONPRNCFEE  ,  XB.BSTYP_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.STAFF_CD  =  NVL(:STAFF_CD,  XA.STAFF_CD)   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XA.NEW_DAY  <  :YRMN  ||  '32'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD  GROUP  BY  XB.BSTYP_CD  )  ZA  ,  (   \n";
        query +=" SELECT  COUNT(A.UPSO_CD)  NONPY_CNT  ,  SUM(B.TOT_DEMD_AMT)  NONPY_AMT  ,  A.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  12)  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_STAT  =  '1'  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.BSTYP_CD  )  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZC.BSTYP_CD  =  'z'   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZA.BSTYP_CD(+)   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.BSTYP_CD(+)   \n";
        query +=" AND  CNT  >0 ";
        sobj.setSql(query);
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ��ü���� ��¡�� ���
    // ��ü ������ ��¡�� ��踦 ��ȸ�Ѵ�
    public DOBJ CALLnonpy_stat_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLnonpy_stat_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.setRetcode(1);
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_stat_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //���� ����
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //���� ����
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TG.GRADNM  ,  NVL(TA.CNT,  0)  CNT1  ,  TA.MONPRNCFEE  ,  NVL(TB.NONPY_CNT,  0)  NONPY_CNT1  ,  NVL(TB.NONPY_AMT,  0)  NONPY_AMT1  ,  NVL(TC.CNT,  0)  CNT2  ,  NVL(TD.NONPY_CNT,  0)  NONPY_CNT2  ,  NVL(TD.NONPY_AMT,  0)  NONPY_AMT2  ,  NVL(TE.CNT,  0)  CNT3  ,  NVL(TF.NONPY_CNT,  0)  NONPY_CNT3  ,  NVL(TF.NONPY_AMT,  0)  NONPY_AMT3  ,  TG.GRAD_GBN  FROM  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  CNT  ,  SUM(XB.MONPRNCFEE)  MONPRNCFEE  ,  XB.BSTYP_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.STAFF_CD  =  NVL(:STAFF_CD,  XA.STAFF_CD)   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XA.NEW_DAY  <  :YRMN  ||  '32'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD  GROUP  BY  XB.BSTYP_CD  )  TA  ,  (   \n";
        query +=" SELECT  COUNT(A.UPSO_CD)  NONPY_CNT  ,  SUM(B.TOT_DEMD_AMT)  NONPY_AMT  ,  A.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  12)  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  NEW_DAY  <=  :YRMN  ||  '31')   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_STAT  =  '1'  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.BSTYP_CD  )  TB  ,  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  CNT  ,  SUM(XB.MONPRNCFEE)  MONPRNCFEE  ,  XB.BSTYP_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.STAFF_CD  =  NVL(:STAFF_CD,  XA.STAFF_CD)   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >=  :YRMN)   \n";
        query +=" AND  XA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XA.NEW_DAY  <  :YRMN  ||  '32'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD  GROUP  BY  XB.BSTYP_CD  )  TC  ,  (   \n";
        query +=" SELECT  COUNT(A.UPSO_CD)  NONPY_CNT  ,  SUM(B.TOT_DEMD_AMT)  NONPY_AMT  ,  A.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  12)  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.BSTYP_CD  )  TD  ,  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  CNT  ,  SUM(XB.MONPRNCFEE)  MONPRNCFEE  ,  XB.BSTYP_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  WHERE  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.STAFF_CD  =  NVL(:STAFF_CD,  XA.STAFF_CD)   \n";
        query +=" AND  XA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (XA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XA.UPSO_STAT  =  '1'  GROUP  BY  XB.BSTYP_CD  )  TE  ,  (   \n";
        query +=" SELECT  COUNT(A.UPSO_CD)  NONPY_CNT  ,  SUM(B.TOT_DEMD_AMT)  NONPY_AMT  ,  A.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  :YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  12)  TOT_DEMD_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :YRMN,  :YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_STAT  =  '1'  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.BSTYP_CD  )  TF  ,  GIBU.TBRA_BSTYPGRAD  TG  WHERE  TG.BSTYP_CD  =  'z'   \n";
        query +=" AND  TA.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TB.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TC.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TD.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TE.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TF.BSTYP_CD(+)  =  TG.GRAD_GBN   \n";
        query +=" AND  TA.CNT  >  0 ";
        sobj.setSql(query);
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$nonpy_stat
    //##**$$end
}
