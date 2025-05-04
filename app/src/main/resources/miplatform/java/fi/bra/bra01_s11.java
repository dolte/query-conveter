
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s11
{
    public bra01_s11()
    {
    }
    //##**$$sel_each_bill
    /* ��꼭 ���� �� ������ û������(���翩�θ� ����)�� ������ ������ �����Ͽ�����, �����α����� �� �����ϼ��� ���� ������ �߰��ϰ� �Ǹ鼭, û�������� ������� ����� �ϵ��� �����ؾ� �ϴ� ��Ȳ�� �߻�. (û���������� �ϸ� �����ᰡ �ƴ� ���� USE_AMT �� �ؾ��ϳ�, ����¡�������� �ݾ��� ũ�� ����)
    ����, û������ ������� ���ο� �ڵ���ü �ΰ����� ������ ������ �� ����¡�� �ݾ��� ����ϵ��� ����
    +++++ ����� �߰������ �߰��ؾ��ؼ� �ݾ׿� NVL�� û�������� �����+�߰������ ���ϵ��� �߰�
    2020-06-09 ������
    */
    public DOBJ CTLsel_each_bill(DOBJ dobj)
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
            dobj  = CALLsel_each_bill_SEL1(Conn, dobj);           //  �����ߺ�üũ�װŷ�ó��������
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
    public DOBJ CTLsel_each_bill( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_each_bill_SEL1(Conn, dobj);           //  �����ߺ�üũ�װŷ�ó��������
        return dobj;
    }
    // �����ߺ�üũ�װŷ�ó��������
    public DOBJ CALLsel_each_bill_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_each_bill_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_each_bill_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("S").getRecord().get("APPTN_YRMN");   //��û �Ͻ�
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XC.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MST  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  :APPTN_YRMN  )  AS  DUPCNT  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  :APPTN_YRMN)  >  0)  THEN  XB.MONPRNCFEE2  -  TRUNC(XB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XB.BSTYP_CD))  ELSE  XB.MONPRNCFEE  END)  +  NVL((SELECT  NVL(ADDT_AMT  +  EADDT_AMT,  0)  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  START_YRMN  =   \n";
        query +=" (SELECT  MIN(START_YRMN)  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :APPTN_YRMN)),  0)  AS  MONPRNCFEE  ,  GIBU.FT_SPLIT(GIBU.FT_GET_DEMD_MONPRNCFEE(XA.UPSO_CD,  :APPTN_YRMN),  ',',  0)  AS  MONPRNCFEE2  ,  SUBSTR(:APPTN_YRMN,0,4)  ||  '��  '  ||  SUBSTR(:APPTN_YRMN,5,2)  ||  '��  ���ǻ���'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_ONOFF_DATA_GBN(UPSO_CD)  LIKE  '%�����α�%'  THEN  MONPRNCFEE  -  TRUNC(MONPRNCFEE  *  0.7,  -1)  ELSE  MONPRNCFEE  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  GIBU.FT_GET_ONOFF_DATA_GBN(UPSO_CD)  LIKE  '%�����α�%'  THEN  MONPRNCFEE2  -  TRUNC(MONPRNCFEE2  *  0.7,  -1)  ELSE  MONPRNCFEE2  END)  AS  MONPRNCFEE2  ,  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  0   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <  10  THEN  0  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  10   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <=  20  THEN  TRUNC(ZB.MONPRNCFEE  *  0.5,  -1)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  0   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <  10  THEN  0  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  10   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <=  20  THEN  TRUNC(ZB.MONPRNCFEE2  *  0.5,  -1)  ELSE  ZB.MONPRNCFEE2  END)  AS  MONPRNCFEE2  ,  ZB.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  )  XB  ,  FIDU.TLEV_BSCON  XC  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  AA.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  AA  ,  (   \n";
        query +=" SELECT  MAX(A.AUTO_NUM)  AS  AUTO_NUM  ,  A.UPSO_CD  ,  A.TERM_YN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BILL_ISS_YN  =  '1'  GROUP  BY  A.UPSO_CD,  A.TERM_YN)  BB  WHERE  BB.TERM_YN  =  'N'   \n";
        query +=" AND  BB.AUTO_NUM  =  AA.AUTO_NUM   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD)  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XC.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MST  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  :APPTN_YRMN  )  AS  DUPCNT  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  :APPTN_YRMN)  >  0)  THEN  TRUNC(XB.MONPRNCFEE2  -  TRUNC(XB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XB.BSTYP_CD))  -  TRUNC((XB.MONPRNCFEE2  -  TRUNC(XB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XB.BSTYP_CD)))  *  0.01,  -1))  ELSE  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y')  >  0  THEN  TRUNC(XB.MONPRNCFEE  -  TRUNC(XB.MONPRNCFEE  *  0.01,  -1))  ELSE  TRUNC(XB.MONPRNCFEE  -  TRUNC(XB.MONPRNCFEE  *  0.01,  -1),  -1)  END)  END)  +  NVL((SELECT  NVL(ADDT_AMT  +  EADDT_AMT,  0)  FROM  GIBU.TBRA_DEMD_AUTO_MM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  START_YRMN  =   \n";
        query +=" (SELECT  MIN(START_YRMN)  FROM  GIBU.TBRA_DEMD_AUTO_MM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :APPTN_YRMN)),  0)  AS  MONPRNCFEE  ,  GIBU.FT_SPLIT(GIBU.FT_GET_DEMD_MONPRNCFEE(XA.UPSO_CD,  :APPTN_YRMN),  ',',  0)  AS  MONPRNCFEE2  ,  SUBSTR(:APPTN_YRMN,0,4)  ||  '��  '  ||  SUBSTR(:APPTN_YRMN,5,2)  ||  '��  ���ǻ���'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_ONOFF_DATA_GBN(UPSO_CD)  LIKE  '%�����α�%'  THEN  MONPRNCFEE  -  TRUNC(MONPRNCFEE  *  0.7,  -1)  ELSE  MONPRNCFEE  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  GIBU.FT_GET_ONOFF_DATA_GBN(UPSO_CD)  LIKE  '%�����α�%'  THEN  MONPRNCFEE2  -  TRUNC(MONPRNCFEE2  *  0.7,  -1)  ELSE  MONPRNCFEE2  END)  AS  MONPRNCFEE2  ,  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  0   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <  10  THEN  0  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  10   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <=  20  THEN  TRUNC(ZB.MONPRNCFEE  *  0.5,  -1)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  0   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <  10  THEN  0  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  >=  10   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  :APPTN_YRMN)  <=  20  THEN  TRUNC(ZB.MONPRNCFEE2  *  0.5,  -1)  ELSE  ZB.MONPRNCFEE2  END)  AS  MONPRNCFEE2  ,  ZB.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  )  XB  ,  FIDU.TLEV_BSCON  XC  ,  GIBU.TBRA_UPSO_AUTO  XE  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XE.TERM_YN  =  'N'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XE.UPSO_CD  IN   \n";
        query +=" (SELECT  AA.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  AA  ,  (   \n";
        query +=" SELECT  MAX(A.AUTO_NUM)  AS  AUTO_NUM  ,  A.UPSO_CD  ,  A.TERM_YN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BILL_ISS_YN  =  '1'  GROUP  BY  A.UPSO_CD,  A.TERM_YN)  BB  WHERE  BB.TERM_YN  =  'N'   \n";
        query +=" AND  BB.AUTO_NUM  =  AA.AUTO_NUM   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD)   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //��û �Ͻ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_each_bill
    //##**$$mng_bill_iss
    /*
    */
    public DOBJ CTLmng_bill_iss(DOBJ dobj)
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
            dobj  = CALLmng_bill_iss_MIUD3(Conn, dobj);           //  �б�
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
    public DOBJ CTLmng_bill_iss( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_bill_iss_MIUD3(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLmng_bill_iss_MIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_bill_iss_SEL1(Conn, dobj);           //  DEMD_NUM �߹�
                dobj  = CALLmng_bill_iss_INS2(Conn, dobj);           //  ��꼭 ������ ���
                dobj  = CALLmng_bill_iss_XIUD11(Conn, dobj);           //  ��꼭 ������ ���
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_bill_iss_UPD12(Conn, dobj);           //  ��꼭 ������ ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_bill_iss_DEL8(Conn, dobj);           //  ��꼭 ������ ����
                dobj  = CALLmng_bill_iss_SEL11(Conn, dobj);           //  �ܿ� �� ��꼭 ����Ȯ��
                if( dobj.getRetObject("SEL11").getRecord().getDouble("CNT") == 0)
                {
                    dobj  = CALLmng_bill_iss_DEL7(Conn, dobj);           //  ��꼭 ������ ����
                }
            }
        }
        return dobj;
    }
    // ��꼭 ������ ����
    public DOBJ CALLmng_bill_iss_DEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bill_iss_DEL8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_DEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //���� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BILL_ISS_DTL  \n";
        query +=" where APPRV_NUM=:APPRV_NUM";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //���� ��ȣ
        return sobj;
    }
    // ��꼭 ������ ����
    public DOBJ CALLmng_bill_iss_UPD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bill_iss_UPD12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_UPD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //���� ��ȣ
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   ISS_DAY = dvobj.getRecord().get("ISS_DAY");   //���� ����
        String   ISS_BRE = dvobj.getRecord().get("ISS_BRE");   //���� ����
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //��꼭 ����
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //û������ȣ
        double   ISS_AMT = dobj.getRetObject("R").getRecord().getDouble("KOMCA_AMT");   //���� �ݾ�
        String   ISS_COMPL_YN = dobj.getRetObject("R").getRecord().get("ISSADD_YN");   //���� �Ϸ� ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BILL_ISS_DTL  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ISS_COMPL_YN=:ISS_COMPL_YN , ISS_AMT=:ISS_AMT , BILL_GBN=:BILL_GBN , ISS_BRE=:ISS_BRE , ISS_DAY=:ISS_DAY , MOD_DATE=SYSDATE , REMAK=:REMAK  \n";
        query +=" where DEMD_NUM=:DEMD_NUM  \n";
        query +=" and APPRV_NUM=:APPRV_NUM";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //���� ��ȣ
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("ISS_DAY", ISS_DAY);               //���� ����
        sobj.setString("ISS_BRE", ISS_BRE);               //���� ����
        sobj.setString("BILL_GBN", BILL_GBN);               //��꼭 ����
        sobj.setString("DEMD_NUM", DEMD_NUM);               //û������ȣ
        sobj.setDouble("ISS_AMT", ISS_AMT);               //���� �ݾ�
        sobj.setString("ISS_COMPL_YN", ISS_COMPL_YN);               //���� �Ϸ� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // DEMD_NUM �߹�
    public DOBJ CALLmng_bill_iss_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_bill_iss_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("S1").getRecord().get("APPTN_YRMN");   //��û �Ͻ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :APPTN_YRMN  ||  LPAD(NVL(MAX(TO_NUMBER(SUBSTR(DEMD_NUM,  7)))  +  1,  0),  4,  '0')  AS  NEW_DEMD_NUM  FROM  GIBU.TBRA_BILL_ISS_MST  WHERE  APPTN_YRMN  =  :APPTN_YRMN ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //��û �Ͻ�
        return sobj;
    }
    // �ܿ� �� ��꼭 ����Ȯ��
    public DOBJ CALLmng_bill_iss_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_bill_iss_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("R").getRecord().get("DEMD_NUM");   //û������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_BILL_ISS_DTL  WHERE  DEMD_NUM  =  :DEMD_NUM ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //û������ȣ
        return sobj;
    }
    // ��꼭 ������ ���
    public DOBJ CALLmng_bill_iss_INS2(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLmng_bill_iss_INS2(dobj, dvobj);
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
    private SQLObject SQLmng_bill_iss_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //��û ����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   APPTN_YRMN = dobj.getRetObject("S1").getRecord().get("APPTN_YRMN");   //��û �Ͻ�
        String   BRAN_CD = dobj.getRetObject("S1").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   DEMD_NUM = dobj.getRetObject("SEL1").getRecord().get("NEW_DEMD_NUM");   //û������ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BILL_ISS_MST (DEMD_NUM, INS_DATE, INSPRES_ID, APPTN_YRMN, UPSO_CD, APPTN_GBN, BRAN_CD, BSCON_CD)  \n";
        query +=" values(:DEMD_NUM , SYSDATE, :INSPRES_ID , :APPTN_YRMN , :UPSO_CD , :APPTN_GBN , :BRAN_CD , :BSCON_CD )";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("APPTN_GBN", APPTN_GBN);               //��û ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //��û �Ͻ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("DEMD_NUM", DEMD_NUM);               //û������ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ��꼭 ������ ���
    public DOBJ CALLmng_bill_iss_XIUD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD11");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bill_iss_XIUD11(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_XIUD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("S1").getRecord().get("APPTN_YRMN");   //��û �Ͻ�
        String   BILL_GBN = dobj.getRetObject("R").getRecord().get("BILL_GBN");   //��꼭 ����
        String   DEMD_NUM = dobj.getRetObject("SEL1").getRecord().get("NEW_DEMD_NUM");   //û������ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   ISS_BRE = dobj.getRetObject("R").getRecord().get("ISS_BRE");   //���� ����
        String   ISS_COMPL_YN = dobj.getRetObject("R").getRecord().get("ISSADD_YN");   //���� �Ϸ� ����
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("ISS_DAY");   //���� ����
        double   KOMCA_AMT = dobj.getRetObject("R").getRecord().getDouble("KOMCA_AMT");   //KOMCA_AMT
        double   MONPRNCFEE2 = dobj.getRetObject("R").getRecord().getDouble("MONPRNCFEE2");   //���ؿ�����
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_BILL_ISS_DTL (DEMD_NUM, APPRV_NUM, BILL_KND, BILL_GBN, SUPPBSCON_CD, ISS_BRE, ISS_AMT, ISS_DAY, REMAK, ISS_COMPL_YN, INSPRES_ID, INS_DATE) SELECT DEMD_NUM , ( SELECT TO_CHAR(SYSDATE, 'YYYYMMDD') || TRIM(TO_CHAR(NVL(MAX(TO_NUMBER(SUBSTR(APPRV_NUM, 9), 'XXX')), 0) + RN, '0XX')) AS APPRV_NUM FROM GIBU.TBRA_BILL_ISS_DTL WHERE APPRV_NUM LIKE TO_CHAR(SYSDATE, 'YYYYMMDD') || '%' AND TO_NUMBER(SUBSTR(APPRV_NUM, 9), 'XXX') > 0 ) AS APPRV_NUM , BILL_KND , BILL_GBN , SUPPBSCON_CD , ISS_BRE , ISS_AMT , ISS_DAY , REMAK , ISS_COMPL_YN , INSPRES_ID , SYSDATE FROM ( SELECT :DEMD_NUM AS DEMD_NUM , ROWNUM AS RN , BILL_KND , :BILL_GBN AS BILL_GBN , SUPPBSCON_CD , :ISS_BRE AS ISS_BRE , TO_NUMBER(ISS_AMT) AS ISS_AMT , :ISS_DAY AS ISS_DAY , :REMAK AS REMAK , :ISS_COMPL_YN AS ISS_COMPL_YN , :INSPRES_ID AS INSPRES_ID FROM ( SELECT 'KOMCA' AS SUPPBSCON_CD , TO_NUMBER(:KOMCA_AMT) AS ISS_AMT , 1 AS R_NUM , '4' AS BILL_KND FROM DUAL UNION ALL SELECT 'T0000001' AS SUPPBSCON_CD , FLOOR(:MONPRNCFEE2 * (SELECT MNG_RATE / 100 FROM GIBU.TBRA_BSCON_MNG_RATE WHERE BSTYP_CD = GIBU.FT_GET_BSTYP_INFO(:UPSO_CD) AND APPL_DAY < :ISS_DAY)) AS ISS_AMT , 2 AS R_NUM , '5' AS BILL_KND FROM DUAL WHERE GIBU.FT_GET_BSTYP_INFO(:UPSO_CD) IN ('k', 'l', 'o') UNION ALL SELECT BSCON_CD , (CASE WHEN BSCON_CD = 'T0000001' THEN FLOOR(:MONPRNCFEE2 * (SELECT MNG_RATE / 100 FROM GIBU.TBRA_BSCON_MNG_RATE WHERE BSTYP_CD = GIBU.FT_GET_BSTYP_INFO(:UPSO_CD) AND APPL_DAY < :ISS_DAY)) ELSE DEMD_AMT END) AS ISS_AMT , ROWNUM + 1 AS R_NUM , (CASE WHEN (SELECT ATAX_YN FROM GIBU.TBRA_BSCON_CONTRINFO WHERE UPSO_CD = A.UPSO_CD AND BSCON_CD = A.BSCON_CD AND BSCON_UPSO_CD = A.BSCON_UPSO_CD AND SEQ = (SELECT MAX(SEQ) FROM GIBU.TBRA_BSCON_CONTRINFO WHERE UPSO_CD = A.UPSO_CD AND BSCON_CD = A.BSCON_CD AND BSCON_UPSO_CD = A.BSCON_UPSO_CD)) = 'Y' THEN '6' ELSE '5' END) AS BILL_KND FROM GIBU.TBRA_BSCON_DEMD_UPLOAD A WHERE DEMD_YRMN = :APPTN_YRMN AND UPSO_CD = :UPSO_CD ) A )";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //��û �Ͻ�
        sobj.setString("BILL_GBN", BILL_GBN);               //��꼭 ����
        sobj.setString("DEMD_NUM", DEMD_NUM);               //û������ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("ISS_BRE", ISS_BRE);               //���� ����
        sobj.setString("ISS_COMPL_YN", ISS_COMPL_YN);               //���� �Ϸ� ����
        sobj.setString("ISS_DAY", ISS_DAY);               //���� ����
        sobj.setDouble("KOMCA_AMT", KOMCA_AMT);               //KOMCA_AMT
        sobj.setDouble("MONPRNCFEE2", MONPRNCFEE2);               //���ؿ�����
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��꼭 ������ ����
    public DOBJ CALLmng_bill_iss_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bill_iss_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bill_iss_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //û������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BILL_ISS_MST  \n";
        query +=" where DEMD_NUM=:DEMD_NUM";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //û������ȣ
        return sobj;
    }
    //##**$$mng_bill_iss
    //##**$$chk_group_bill
    /*
    */
    public DOBJ CTLchk_group_bill(DOBJ dobj)
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
            dobj  = CALLchk_group_bill_SEL1(Conn, dobj);           //  üũ
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
    public DOBJ CTLchk_group_bill( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLchk_group_bill_SEL1(Conn, dobj);           //  üũ
        return dobj;
    }
    // üũ
    public DOBJ CALLchk_group_bill_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLchk_group_bill_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_group_bill_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("S").getRecord().get("APPTN_YRMN");   //��û �Ͻ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  DUPCNT,  NVL(MAX((SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MST  AA  ,  GIBU.TBRA_BILL_ISS_DTL  BB  WHERE  AA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  AA.DEMD_NUM  =  BB.DEMD_NUM   \n";
        query +=" AND  BB.ISS_COMPL_YN  =  '2'   \n";
        query +=" AND  AA.APPTN_GBN  ='1'  )),0)  AS  ISS_COMPL_YN_CNT  FROM  GIBU.TBRA_BILL_ISS_MST  A  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  APPTN_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  APPTN_GBN  ='1' ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //��û �Ͻ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$chk_group_bill
    //##**$$save_group_bill
    /*
    */
    public DOBJ CTLsave_group_bill(DOBJ dobj)
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
            dobj  = CALLsave_group_bill_OSP13(Conn, dobj);           //  ��ü��꼭�߱� ���ν���
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
    public DOBJ CTLsave_group_bill( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_group_bill_OSP13(Conn, dobj);           //  ��ü��꼭�߱� ���ν���
        return dobj;
    }
    // ��ü��꼭�߱� ���ν���
    public DOBJ CALLsave_group_bill_OSP13(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP13");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"BRAN_CD","APPTN_YRMN","ISS_DAY","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //����� ID
            record.put("INSPRES_ID",INSPRES_ID);
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
        String   spname ="GIBU.SP_PROC_DEMD_BILL";
        int[]    intypes={12, 12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.setName("OSP13");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$save_group_bill
    //##**$$sel_bill_iss
    /*
    */
    public DOBJ CTLsel_bill_iss(DOBJ dobj)
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
            dobj  = CALLsel_bill_iss_SEL1(Conn, dobj);           //  ��꼭 ��ȸ
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
    public DOBJ CTLsel_bill_iss( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bill_iss_SEL1(Conn, dobj);           //  ��꼭 ��ȸ
        return dobj;
    }
    // ��꼭 ��ȸ
    public DOBJ CALLsel_bill_iss_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_bill_iss_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bill_iss_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("S").getRecord().get("APPTN_YRMN");   //��û �Ͻ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_NUM  ,  A.UPSO_CD  ,  B.APPRV_NUM  ,  C.UPSO_NM  ,  A.BSCON_CD  ,  D.BSCONHAN_NM  ,  A.BRAN_CD  ,  E.DEPT_NM  AS  BRAN_NM  ,  A.APPTN_YRMN  ,  B.ISS_BRE  ,  B.ISS_AMT  AS  KOMCA_AMT  ,   \n";
        query +=" (SELECT  SUM(ISS_AMT)  FROM  GIBU.TBRA_BILL_ISS_DTL  WHERE  DEMD_NUM  =  A.DEMD_NUM)  AS  ISS_AMT  ,  B.BILL_GBN  ,  B.REMAK  ,  B.ISS_DAY  ,  DECODE((SELECT  COUNT(1)  FROM  FIDU.BILL_TRANS  WHERE  BILLSEQ  =  B.BILL_NUM),  0,  1,  1,  2)  AS  ISS_COMPL_YN  ,  NVL(B.ISS_COMPL_YN,  0)  AS  ISSADD_YN  ,  B.BILL_NUM  ,  DECODE(A.APPTN_GBN,  '1',  '��ü',  '2',  '����')  APPTN_GBN  ,  B.BILL_KND  ,  B.SUPPBSCON_CD  ,  (CASE  WHEN  B.SUPPBSCON_CD  =  'KOMCA'  THEN  '(��)�ѱ��������۱���ȸ'  ELSE   \n";
        query +=" (SELECT  HANMB_NM  FROM  FIDU.TMEM_MB  WHERE  MB_CD  =  B.SUPPBSCON_CD)  END)  AS  SUPPBSCON_NM  ,  FIDU.GET_STAFF_NM(C.STAFF_CD)  AS  STAFF_NM  FROM  GIBU.TBRA_BILL_ISS_MST  A  ,  GIBU.TBRA_BILL_ISS_DTL  B  ,  GIBU.TBRA_UPSO  C  ,  FIDU.TLEV_BSCON  D  ,  INSA.TCPM_DEPT  E  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  A.BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.DEMD_NUM  =  B.DEMD_NUM   \n";
        query +=" AND  B.BILL_KND  IN  ('4',  '5',  '6')   \n";
        query +=" AND  A.APPTN_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  C.BRAN_CD  =  A.BRAN_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  D.BSCON_CD  =  A.BSCON_CD   \n";
        query +=" AND  E.GIBU  =  A.BRAN_CD  ORDER  BY  C.BRAN_CD,  A.INS_DATE,  A.BSCON_CD,  B.SUPPBSCON_CD ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //��û �Ͻ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_bill_iss
    //##**$$bra01_s11_groupIssue
    /* * ���α׷��� : bra01_s11
    * �ۼ��� : 999999
    * �ۼ��� : 2009/10/09
    * ���� :
    * ������1: 2010/02/10
    * ������ :  ������
    * �������� : �ڵ���ü ���Ҵ� �������� 1% ���ε� �ݾ��� �����ش�.
    * ������2: 2010/02/18
    * ������ :  ������
    * �������� : ��꼭 ����ݾ��� �ش���� û���� �ݾ����� ǥ���ش޶�.
    */
    public DOBJ CTLbra01_s11_groupIssue(DOBJ dobj)
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
            dobj  = CALLbra01_s11_groupIssue_DEL2(Conn, dobj);           //  ���������ͻ���
            dobj  = CALLbra01_s11_groupIssue_SEL1(Conn, dobj);           //  �뷮��û��ȸ
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
    public DOBJ CTLbra01_s11_groupIssue( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_groupIssue_DEL2(Conn, dobj);           //  ���������ͻ���
        dobj  = CALLbra01_s11_groupIssue_SEL1(Conn, dobj);           //  �뷮��û��ȸ
        return dobj;
    }
    // ���������ͻ���
    public DOBJ CALLbra01_s11_groupIssue_DEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra01_s11_groupIssue_DEL2(dobj, dvobj);
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
    private SQLObject SQLbra01_s11_groupIssue_DEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPTN_YRMN ="";        //��û �Ͻ�
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   APPTN_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //��û �Ͻ�
        String   APPTN_GBN ="1";   //��û ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" where APPTN_YRMN=SUBSTR(:APPTN_YRMN_1,0,6)  \n";
        query +=" and APPTN_GBN=:APPTN_GBN  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //��û �Ͻ�
        sobj.setString("APPTN_GBN", APPTN_GBN);               //��û ����
        return sobj;
    }
    // �뷮��û��ȸ
    public DOBJ CALLbra01_s11_groupIssue_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra01_s11_groupIssue_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_groupIssue_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ISSUE_YRMN = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");        //���� ���
        String   ISSUE_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //���� ���
        String   ISS_DAY = dobj.getRetObject("S").getRecord().get("ISS_DAY");   //���� ����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  XA.BRAN_CD  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),0,4)  ||  '��  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),5,2)  ||  '��  ���ǻ���'  AS  ISS_BRE  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,0,6))  =  0)   \n";
        query +=" AND  ((SELECT  COUNT(1)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  XA.UPSO_CD)  >  0)  THEN  XD.MONPRNCFEE  WHEN  GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,0,6))  >  0  THEN  XD.MONPRNCFEE  -  TRUNC(XD.MONPRNCFEE  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XD.BSTYP_CD))  ELSE  XD.TOT_DEMD_AMT  END)  AS  ISS_AMT  ,  2  AS  BILL_GBN  ,  0  AS  ISS_COMPL_YN  ,  'I'  AS  CRUD  ,  ''  AS  REMAK  ,  '0'  AS  ISSADD_YN  ,  :ISS_DAY  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_DEMD_OCR  XD  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XD.DEMD_YRMN  =  SUBSTR(:ISSUE_YRMN_1,0,6)   \n";
        query +=" AND  XD.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.DEMD_GBN  =  '32'   \n";
        query +=" AND  XD.TOT_DEMD_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  XA.BRAN_CD  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),0,4)  ||  '��  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),5,2)  ||  '��  ���ǻ���'  AS  ISS_BRE  ,  XF.MONPRNCFEE  AS  ISS_AMT  ,  2  AS  BILL_GBN  ,  0  AS  ISS_COMPL_YN  ,  'I'  AS  CRUD  ,  ''  AS  REMAK  ,  '0'  AS  ISSADD_YN  ,  :ISS_DAY  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZB.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,0,6))  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XF  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XF.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  SUBSTR(:ISSUE_YRMN_1,0,6)   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  DEMD_GBN  =  '32'   \n";
        query +=" AND  TOT_DEMD_AMT  >  0  )   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN  (   \n";
        query +=" SELECT  AA.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  AA  ,  (   \n";
        query +=" SELECT  MAX(A.AUTO_NUM)  AUTO_NUM  ,  A.UPSO_CD  ,  A.TERM_YN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BILL_ISS_YN  =  '1'  GROUP  BY  A.UPSO_CD,  A.TERM_YN  )  BB  WHERE  BB.TERM_YN  =  'N'   \n";
        query +=" AND  BB.AUTO_NUM  =  AA.AUTO_NUM   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD  )  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  XA.BRAN_CD  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),0,4)  ||  '��  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),5,2)  ||  '��  ���ǻ���'  AS  ISS_BRE  ,  XE.TOT_DEMD_AMT  AS  ISS_AMT  ,  2  AS  BILL_GBN  ,  0  AS  ISS_COMPL_YN  ,  'I'  AS  CRUD  ,  ''  AS  REMAK  ,  '0'  AS  ISSADD_YN  ,  :ISS_DAY  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_DEMD_AUTO  XE  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XE.DEMD_YRMN  =  SUBSTR(:ISSUE_YRMN_1,0,6)   \n";
        query +=" AND  XE.DEMD_GBN  =  '31'   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.TOT_DEMD_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  XA.BRAN_CD  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),0,4)  ||  '��  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),5,2)  ||  '��  ���ǻ���'  AS  ISS_BRE  ,  TRUNC(XF.MONPRNCFEE  -  TRUNC(XF.MONPRNCFEE  *  0.01,  -1),  -1)  AS  ISS_AMT  ,  2  AS  BILL_GBN  ,  0  AS  ISS_COMPL_YN  ,  'I'  AS  CRUD  ,  ''  AS  REMAK  ,  '0'  AS  ISSADD_YN  ,  :ISS_DAY  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_UPSO_AUTO  XE  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZB.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,0,6))  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XF  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XE.TERM_YN  =  'N'   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  DEMD_YRMN  =  SUBSTR(:ISSUE_YRMN_1,0,6)   \n";
        query +=" AND  DEMD_GBN  =  '31'   \n";
        query +=" AND  TOT_DEMD_AMT  >  0  )   \n";
        query +=" AND  XE.UPSO_CD  IN  (   \n";
        query +=" SELECT  AA.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  AA  ,  (   \n";
        query +=" SELECT  MAX(A.AUTO_NUM)  AUTO_NUM  ,  A.UPSO_CD  ,  A.TERM_YN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BILL_ISS_YN  =  '1'  GROUP  BY  A.UPSO_CD,  A.TERM_YN  )  BB  WHERE  BB.TERM_YN  =  'N'   \n";
        query +=" AND  BB.AUTO_NUM  =  AA.AUTO_NUM   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD  )   \n";
        query +=" AND  XF.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD  ORDER  BY  BSCON_CD,  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("ISSUE_YRMN_1", ISSUE_YRMN_1);               //���� ���
        sobj.setString("ISS_DAY", ISS_DAY);               //���� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$bra01_s11_groupIssue
    //##**$$bra01_s11_dupCheck
    /* * ���α׷��� : bra01_s11
    * �ۼ��� : 999999
    * �ۼ��� : 2009/10/08
    * ���� :
    * ������1: 2010/02/10
    * ������ :
    * �������� : �ڵ���ü ������ ��� �����ῡ�� 1% ���ε� �ݾ��� �����ش�.
    EX1) �ڵ���ü ����  :3009414A(������: 300,000 -> ǥ�ñݾ�: 270,000)
    EX2) �Ϲݾ���  : 3010399A (������: 70,000 -> ǥ�ñݾ�: 70,000)
    * ������2: 2010/04/22
    * ������ :
    * �������� : �����߱޽ÿ��� ������������ ��꼭���࿩��üũ ǥ�ÿ� ������� �ŷ�ó ������ ��ϵǾ� ������ �ȴ�.
    ���� �ش� ���� û�������� �־�� ��.
    * ������2: 2010/04/27
    * ������ :
    * �������� : �ű԰��߾��ҵ� ��꼭�߱��� �����ϴ�. �� û�������� ���� ��쿡�� �ش������ �����Ḧ  �����ش�.
    */
    public DOBJ CTLbra01_s11_dupCheck(DOBJ dobj)
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
            dobj  = CALLbra01_s11_dupCheck_SEL11(Conn, dobj);           //  �����ߺ�üũ�װŷ�ó��������
            if( dobj.getRetObject("SEL11").getRecordCnt() == 0)
            {
                dobj  = CALLbra01_s11_dupCheck_SEL3(Conn, dobj);           //  �ű԰��߾��ҿ���������
                dobj  = CALLbra01_s11_dupCheck_SEL1( dobj);        //  �������
            }
            else
            {
                dobj  = CALLbra01_s11_dupCheck_SEL1( dobj);        //  �������
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
    public DOBJ CTLbra01_s11_dupCheck( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_dupCheck_SEL11(Conn, dobj);           //  �����ߺ�üũ�װŷ�ó��������
        if( dobj.getRetObject("SEL11").getRecordCnt() == 0)
        {
            dobj  = CALLbra01_s11_dupCheck_SEL3(Conn, dobj);           //  �ű԰��߾��ҿ���������
            dobj  = CALLbra01_s11_dupCheck_SEL1( dobj);        //  �������
        }
        else
        {
            dobj  = CALLbra01_s11_dupCheck_SEL1( dobj);        //  �������
        }
        return dobj;
    }
    // �����ߺ�üũ�װŷ�ó��������
    public DOBJ CALLbra01_s11_dupCheck_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra01_s11_dupCheck_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_dupCheck_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ISSUE_YRMN ="";        //���� ���
        String   ISSUE_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //���� ���
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  SUBSTR(:ISSUE_YRMN_1,1,6)  )  AS  DUPCNT  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,1,6))  =  0)   \n";
        query +=" AND  ((SELECT  COUNT(1)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  XA.UPSO_CD)  >  0)  THEN  XD.MONPRNCFEE  WHEN  GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,1,6))  >  0  THEN  XD.MONPRNCFEE  -  TRUNC(XD.MONPRNCFEE  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XD.BSTYP_CD))  ELSE  XD.TOT_DEMD_AMT  END)  AS  MONPRNCFEE  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,1,6),0,4)  ||  '��  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,1,6),5,2)  ||  '��  ���ǻ���'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_DEMD_OCR  XD  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XD.DEMD_YRMN  =  SUBSTR(:ISSUE_YRMN_1,1,6)   \n";
        query +=" AND  XD.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.DEMD_GBN  =  '32'   \n";
        query +=" AND  XD.TOT_DEMD_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  SUBSTR(:ISSUE_YRMN_1,1,6)  )  AS  DUPCNT  ,  XE.TOT_DEMD_AMT  AS  MONPRNCFEE  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,1,6),0,4)  ||  '��  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,1,6),5,2)  ||  '��  ���ǻ���'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_DEMD_AUTO  XE  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XE.DEMD_YRMN  =  SUBSTR(:ISSUE_YRMN_1,1,6)   \n";
        query +=" AND  XE.DEMD_GBN  =  '31'   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.TOT_DEMD_AMT  >  0  ORDER  BY  BSCON_CD,  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("ISSUE_YRMN_1", ISSUE_YRMN_1);               //���� ���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �ű԰��߾��ҿ���������
    // û������ ���� �� ��ϵ� �ű԰��߾����ΰ�� �����Ḧ �����ش�. 2010.04.26 �����̰����
    public DOBJ CALLbra01_s11_dupCheck_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra01_s11_dupCheck_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_dupCheck_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ISSUE_YRMN ="";        //���� ���
        String   ISSUE_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //���� ���
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XC.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  SUBSTR(:ISSUE_YRMN_1,1,6)  )  AS  DUPCNT  ,  XB.MONPRNCFEE  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,1,6),0,4)  ||  '��  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,1,6),5,2)  ||  '��  ���ǻ���'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZB.UPSO_CD,  SUBSTR(:ISSUE_YRMN_1,1,6))  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  FIDU.TLEV_BSCON  XC  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSCON_CD  =  XA.BSCON_CD ";
        sobj.setSql(query);
        sobj.setString("ISSUE_YRMN_1", ISSUE_YRMN_1);               //���� ���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������
    public DOBJ CALLbra01_s11_dupCheck_SEL1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL11, SEL3","");
        rvobj.setName("SEL1") ;
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    //##**$$bra01_s11_dupCheck
    //##**$$bra01_s11_groupDupCheck
    /* * ���α׷��� : bra01_s11
    * �ۼ��� : 999999
    * �ۼ��� : 2009/10/08
    * ���� : 1.��ü�߱޽� �ش����� �̹� ��ü�߱�(APPTN_GBN='1') ���� ��꼭������ �Ϸ�Ǿ��� ��쿡�� �����Ҽ� ���� ��� ������.
    - ISS_COMPL_YN_CNT �� üũ
    2.��ü�߱޽� �ش����� �̹� ��ü�߱�(APPTN_GBN='1') ���� ��꼭��û�� �Ǿ����� ��� �����ϰ� �ٽ� �߱��ϳĴ� �ȳ����� �����ش�.
    - DUPCNT �� üũ
    * ������1: �ǳ���
    * ������ : 2010/06/11
    * �������� : ISS_COMPL_YN_CNT üũ�� ISS_COMPL_YN = '1' => ISS_COMPL_YN = '2'�� ����
    */
    public DOBJ CTLbra01_s11_groupDupCheck(DOBJ dobj)
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
            dobj  = CALLbra01_s11_groupDupCheck_SEL1(Conn, dobj);           //  ��ü�߱��ߺ�üũ
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
    public DOBJ CTLbra01_s11_groupDupCheck( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_groupDupCheck_SEL1(Conn, dobj);           //  ��ü�߱��ߺ�üũ
        return dobj;
    }
    // ��ü�߱��ߺ�üũ
    public DOBJ CALLbra01_s11_groupDupCheck_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra01_s11_groupDupCheck_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_groupDupCheck_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPTN_YRMN ="";        //��û �Ͻ�
        String   APPTN_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //��û �Ͻ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  DUPCNT,  NVL(MAX((SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  AA  WHERE  AA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  SUBSTR(:APPTN_YRMN_1,1,6)   \n";
        query +=" AND  ISS_COMPL_YN  =  '2'   \n";
        query +=" AND  APPTN_GBN  ='1'  )),0)  AS  ISS_COMPL_YN_CNT  FROM  GIBU.TBRA_BILL_ISS_MNG  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  APPTN_YRMN  =  SUBSTR(:APPTN_YRMN_1,1,6)   \n";
        query +=" AND  APPTN_GBN  ='1' ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //��û �Ͻ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$bra01_s11_groupDupCheck
    //##**$$bra01_s11_delete_chk
    /* �ӽ�- ������� ���� ���������� 2010.04.26
    */
    public DOBJ CTLbra01_s11_delete_chk(DOBJ dobj)
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
            dobj  = CALLbra01_s11_delete_chk_SEL1(Conn, dobj);           //  �������ɿ���üũ
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
    public DOBJ CTLbra01_s11_delete_chk( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_delete_chk_SEL1(Conn, dobj);           //  �������ɿ���üũ
        return dobj;
    }
    // �������ɿ���üũ
    // ��Źȸ�� ��꼭 ���� ���̺��� �ش� ��꼭��ȣ�� �����Ǿ����� üũ
    public DOBJ CALLbra01_s11_delete_chk_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra01_s11_delete_chk_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_delete_chk_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //��꼭 ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  FIDU.TTAC_BILL  WHERE  BILL_NUM  =  :BILL_NUM ";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //��꼭 ��ȣ
        return sobj;
    }
    //##**$$bra01_s11_delete_chk
    //##**$$bra01_s11_dupCheck02
    /* * ���α׷��� : bra01_s11
    * �ۼ��� : 999999
    * �ۼ��� : 2009/10/08
    * ���� :
    * ������1: 2010.02.10
    * ������ : ������
    * �������� : �ڵ���ü ������ ��� �����ῡ�� 1% ���ε� �ݾ��� �����ش�.
    EX1) �ڵ���ü ���� �ŷ�ó �ڵ� : J1227 (������: 300,000 -> ǥ�ñݾ�: 270,000)
    EX2) �Ϲݾ��� �ŷ�ó �ڵ� : J1292 (������: 70,000 -> ǥ�ñݾ�: 70,000)
    �����ϴ� METHOD�ε�;;;;
    */
    public DOBJ CTLbra01_s11_dupCheck02(DOBJ dobj)
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
            dobj  = CALLbra01_s11_dupCheck02_SEL1(Conn, dobj);           //  �ߺ�üũ�װŷ�ó����üũ
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
    public DOBJ CTLbra01_s11_dupCheck02( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_dupCheck02_SEL1(Conn, dobj);           //  �ߺ�üũ�װŷ�ó����üũ
        return dobj;
    }
    // �ߺ�üũ�װŷ�ó����üũ
    public DOBJ CALLbra01_s11_dupCheck02_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra01_s11_dupCheck02_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_dupCheck02_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ISSUE_YRMN ="";        //���� ���
        String   ISSUE_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //���� ���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.MONPRNCFEE  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  APPTN_YRMN  =  SUBSTR(:ISSUE_YRMN_1,0,6)  )  DUPCNT  ,  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),0,4)  ||  '��  '  ||  SUBSTR(SUBSTR(:ISSUE_YRMN_1,0,6),5,2)  ||  '��  ���ǻ���'  AS  CTENT  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  DECODE(ZD.UPSO_CD,  NULL,  ZB.MONPRNCFEE,  (ZB.MONPRNCFEE*0.99))  MONPRNCFEE  ,  ZC.GRADNM  ,  ZB.BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ,  A.UPSO_CD  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.APPL_DAY  <=  TO_CHAR(SYSDATE,  'YYYYMMDD')  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_UPSO_AUTO  ZD  WHERE  ZA.UPSO_CD  =  ZB.UPSO_CD   \n";
        query +=" AND  ZA.JOIN_SEQ  =  ZB.JOIN_SEQ   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  'z'   \n";
        query +=" AND  ZD.TERM_YN(+)  ='N'   \n";
        query +=" AND  ZD.UPSO_CD(+)  =  ZB.UPSO_CD  )  XB  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("ISSUE_YRMN_1", ISSUE_YRMN_1);               //���� ���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    //##**$$bra01_s11_dupCheck02
    //##**$$end
}
