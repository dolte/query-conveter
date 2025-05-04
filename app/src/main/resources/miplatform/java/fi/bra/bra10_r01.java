
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_r01
{
    public bra10_r01()
    {
    }
    //##**$$bscon_create_file
    /*
    */
    public DOBJ CTLbscon_create_file(DOBJ dobj)
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
            dobj  = CALLbscon_create_file_SEL1(Conn, dobj);           //  ���ϻ���
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
    public DOBJ CTLbscon_create_file( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbscon_create_file_SEL1(Conn, dobj);           //  ���ϻ���
        return dobj;
    }
    // ���ϻ���
    // 2017.12.15 �̴ټ� ���� 1. û���� �ǰ� �Ա��� �ȵ� �ǵ� ǥ��   1-1. �����ڵ忡�� 'PROC_AMT(REPT_AMT�� ���)>0' ����   1-2. �Ա��� ���°�� MAPPING�� �����Ƿ� DECODE�߰� �Ͽ� ó������� û������� ������ ���� 2. ��꼭 ���� ���� ǥ��   2-1. MYB�� ����Ͽ� ��꼭 ���� ���� ǥ��  2017.12.19 �̴ټ� ���� 1. û������� ó������� ���� �� ��� 2. ���γ���� ó������� ���� �� ��� 3. ���γ���� ó��������� ū ��� �Ա��� �־ ǥ������ ����  2017.12.27 �̴ټ� ���� 1. ����¡ó�� ���� ����  2017.12.28 �������� ���ǿ�,���������� �б� �������� ����ó�� �Աݵ� �͸� ǥ��, �������� û���� �Ȱ͵� ǥ��
    public DOBJ CALLbscon_create_file_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbscon_create_file_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbscon_create_file_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PAGECNT = dobj.getRetObject("S").getRecord().get("PAGECNT");   //��������ȸ�Ǽ�
        String   PROC_YRMN = dobj.getRetObject("S").getRecord().get("PROC_YRMN");   //ó�� ���
        double   PAGENUMBER = dobj.getRetObject("S").getRecord().getDouble("PAGENUMBER");   //������ �ѹ���
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEMD_YRMN  ,  BSTYP_NM  ,  BSCON_CD  ,  BSCON_UPSO_CD  ,  BSCON_UPSO_NM  ,  DEMD_DAY  ,  DEMD_AMT  ,  CASE  WHEN  TO_NUMBER(MAPPING_YRMN)  >  TO_NUMBER(:PROC_YRMN)  THEN  ''  ELSE  REPT_YRMN  END  REPT_YRMN  ,  CASE  WHEN  TO_NUMBER(MAPPING_YRMN)  >  TO_NUMBER(:PROC_YRMN)  THEN  ''  ELSE  REPT_DAY  END  REPT_DAY  ,  CASE  WHEN  TO_NUMBER(MAPPING_YRMN)  >  TO_NUMBER(:PROC_YRMN)  THEN  0  ELSE  REPT_AMT  END  REPT_AMT  ,  RETURN_YRMN  ,  RETURN_DAY  ,  RETURN_AMT  ,  BIGO  ,  TOT_CNT  ,  BILL_YN  FROM  (   \n";
        query +=" SELECT  ROWNUM  AS  RNUM  ,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  A.BSTYP_CD)  AS  BSTYP_NM  ,  DEMD_YRMN  ,  BSCON_CD  ,  BSCON_UPSO_CD  ,  BSCON_UPSO_NM  ,  DEMD_DAY  ,  DEMD_AMT  ,  REPT_YRMN  ,  REPT_DAY  ,  REPT_AMT  ,  RETURN_YRMN  ,  RETURN_DAY  ,  RETURN_AMT  ,  BIGO  ,  TOT_CNT  ,  UPSO_CD  ,  MAPPING_YRMN  ,  BILL_YN  FROM  (   \n";
        query +=" SELECT  MYA.DEMD_YRMN  ,  MYA.BSCON_CD  ,  MYA.BSCON_UPSO_CD  ,  MYA.BSCON_UPSO_NM  ,  MYA.DEMD_DAY  ,  MYA.DEMD_AMT  ,  MYA.REPT_YRMN  ,  MYA.REPT_DAY  ,  MYA.REPT_AMT  ,  MYA.RETURN_YRMN  ,  MYA.RETURN_DAY  ,  MYA.RETURN_AMT  ,  MYA.BIGO  ,  MYA.BSTYP_CD  ,  COUNT(0)  OVER  ()  AS  TOT_CNT  ,  MYA.UPSO_CD  ,  MYA.MAPPING_YRMN  ,  DECODE(BILL_NUM,  NULL,  'N',  'Y')  AS  BILL_YN  FROM  (   \n";
        query +=" SELECT  A.DEMD_YRMN  ,  A.BSCON_CD  ,   \n";
        query +=" (SELECT  MAX(BSCON_UPSO_CD)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  BSCON_CD  =  A.BSCON_CD)  AS  BSCON_UPSO_CD  ,   \n";
        query +=" (SELECT  MAX(BSCON_UPSO_NM)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  BSCON_CD  =  A.BSCON_CD)  AS  BSCON_UPSO_NM  ,  A.DEMD_DAY  ,  A.DEMD_AMT  ,  A.REPT_YRMN  ,  A.REPT_DAY  ,  A.PROC_AMT  AS  REPT_AMT  ,  ''  AS  RETURN_YRMN  ,  ''  AS  RETURN_DAY  ,  0  AS  RETURN_AMT  ,  ''  AS  BIGO  ,  A.BSTYP_CD  ,  A.UPSO_CD  ,  A.MAPPING_YRMN  FROM  GIBU.TBRA_DEMD_REPT_BSCON  A  WHERE  (DECODE(:BSCON_CD,  'T0000001',  MAPPING_YRMN,  DECODE(MAPPING_YRMN,  NULL,  DEMD_YRMN,  MAPPING_YRMN))  =  :PROC_YRMN  )   \n";
        query +=" AND  A.BSCON_CD  =  :BSCON_CD  UNION  ALL   \n";
        query +=" SELECT  ''  AS  DEMD_YRMN  ,  A.BSCON_CD  ,   \n";
        query +=" (SELECT  MAX(BSCON_UPSO_CD)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  BSCON_CD  =  A.BSCON_CD)  AS  BSCON_UPSO_CD  ,   \n";
        query +=" (SELECT  MAX(BSCON_UPSO_NM)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  BSCON_CD  =  A.BSCON_CD)  AS  BSCON_UPSO_NM  ,  ''  AS  DEMD_DAY  ,  0  AS  DEMD_AMT  ,  ''  AS  REPT_YRMN  ,  ''  AS  REPT_DAY  ,  0  AS  REPT_AMT  ,  A.RETURN_YRMN  ,  A.RETURN_DAY  ,  A.PROC_AMT  AS  RETURN_AMT  ,  C.REMAK  AS  BIGO  ,  A.BSTYP_CD  ,  A.UPSO_CD  ,  ''  AS  MAPPING_YRMN  FROM  GIBU.TBRA_BSCON_RETURN  A  ,  GIBU.TBRA_REPT_RETURN  C  WHERE  A.RETURN_DAY  =  C.RETURN_DAY   \n";
        query +=" AND  A.RETURN_NUM  =  C.RETURN_NUM   \n";
        query +=" AND  A.RETURN_YRMN  =  :PROC_YRMN   \n";
        query +=" AND  A.BSCON_CD  =  :BSCON_CD  )  MYA,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUPPBSCON_CD  ,  MAX(BILL_NUM)  AS  BILL_NUM  FROM  GIBU.TBRA_BILL_ISS_MST  A  ,  GIBU.TBRA_BILL_ISS_DTL  B  WHERE  A.DEMD_NUM  =  B.DEMD_NUM   \n";
        query +=" AND  B.BILL_KND  IN  ('4',  '5',  '6')   \n";
        query +=" AND  A.APPTN_YRMN  =  :PROC_YRMN   \n";
        query +=" AND  SUPPBSCON_CD  =  :BSCON_CD  GROUP  BY  UPSO_CD,  SUPPBSCON_CD  )  MYB  WHERE  MYA.UPSO_CD  =  MYB.UPSO_CD(+)   \n";
        query +=" AND  (DEMD_AMT  !=0   \n";
        query +=" OR  REPT_AMT  !=0   \n";
        query +=" OR  RETURN_AMT  !=0)  ORDER  BY  UPSO_CD,  DEMD_DAY  )  A  WHERE  ROWNUM  <=  TO_NUMBER(:PAGENUMBER)  *  TO_NUMBER(:PAGECNT)  )  WHERE  RNUM  BETWEEN  ((TO_NUMBER(:PAGENUMBER)  -  1)  *  TO_NUMBER(:PAGECNT)  +  1)   \n";
        query +=" AND  (TO_NUMBER(:PAGENUMBER)  *  TO_NUMBER(:PAGECNT)) ";
        sobj.setSql(query);
        sobj.setString("PAGECNT", PAGECNT);               //��������ȸ�Ǽ�
        sobj.setString("PROC_YRMN", PROC_YRMN);               //ó�� ���
        sobj.setDouble("PAGENUMBER", PAGENUMBER);               //������ �ѹ���
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    //##**$$bscon_create_file
    //##**$$sel_bscon_rept
    /*
    */
    public DOBJ CTLsel_bscon_rept(DOBJ dobj)
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
            dobj  = CALLsel_bscon_rept_SEL1(Conn, dobj);           //  �Աݳ�����ȸ
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
    public DOBJ CTLsel_bscon_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bscon_rept_SEL1(Conn, dobj);           //  �Աݳ�����ȸ
        return dobj;
    }
    // �Աݳ�����ȸ
    // 2017.12.19 �̴ټ� ���� ���ϻ�������� �޶����� ���� ���� ���� û�� : �Էµ� ó������� û���� ���� �� �Ա� : �Էµ� ó������� ���ε� ���� �� ��ȯ : �Էµ� ó������� ��ȯ�� ���� ��  2017.12.28 �̴ټ� ���� �������� ���ǿ�, ������ �б�� ������ û���� �ȸ���
    public DOBJ CALLsel_bscon_rept_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_bscon_rept_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bscon_rept_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_YRMN = dobj.getRetObject("S").getRecord().get("PROC_YRMN");   //ó�� ���
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  AS  PROC_YRMN  ,  BSCON_CD  ,  NVL(SUM(DEMD_AMT),  0)  AS  DEMD_AMT  ,  NVL(SUM(REPT_AMT),  0)  AS  REPT_AMT  ,  NVL(SUM(RETURN_AMT),  0)  AS  RETURN_AMT  FROM  (   \n";
        query +=" SELECT  DEMD_YRMN  AS  YRMN  ,  BSCON_CD  ,  SUM(DEMD_AMT)  AS  DEMD_AMT  ,  0  AS  REPT_AMT  ,  0  AS  RETURN_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  DEMD_YRMN  =  :PROC_YRMN  GROUP  BY  DEMD_YRMN,  BSCON_CD  UNION  ALL   \n";
        query +=" SELECT  MAPPING_YRMN  AS  YRMN  ,  BSCON_CD  ,  0  AS  DEMD_AMT  ,  SUM(PROC_AMT)  AS  REPT_AMT  ,  0  AS  RETURN_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :PROC_YRMN  GROUP  BY  MAPPING_YRMN,  BSCON_CD  UNION  ALL   \n";
        query +=" SELECT  RETURN_YRMN  AS  YRMN  ,  BSCON_CD  ,  0  AS  DEMD_AMT  ,  0  AS  REPT_AMT  ,  SUM(PROC_AMT)  AS  RETURN_AMT  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :PROC_YRMN  GROUP  BY  RETURN_YRMN,  BSCON_CD  )  WHERE  1=1   \n";
        query +=" AND  BSCON_CD  =  NVL(:BSCON_CD,BSCON_CD)  GROUP  BY  YRMN,  BSCON_CD  ORDER  BY  BSCON_CD ";
        sobj.setSql(query);
        sobj.setString("PROC_YRMN", PROC_YRMN);               //ó�� ���
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    //##**$$sel_bscon_rept
    //##**$$end
}
