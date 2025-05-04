
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_p09
{
    public bra04_p09()
    {
    }
    //##**$$sel_day
    /*
    */
    public DOBJ CTLsel_day(DOBJ dobj)
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
            dobj  = CALLsel_day_SEL1(Conn, dobj);           //  MAX ����ϱ��ϱ�
            if(!dobj.getRetObject("SEL1").getRecord().get("DISP_DAY").equals(""))
            {
                dobj  = CALLsel_day_SEL3(Conn, dobj);           //  ��������
                dobj  = CALLsel_day_SEL2( dobj);        //  �������
            }
            else
            {
                dobj  = CALLsel_day_SEL4(Conn, dobj);           //  ��������
                dobj  = CALLsel_day_SEL2( dobj);        //  �������
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
    public DOBJ CTLsel_day( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_day_SEL1(Conn, dobj);           //  MAX ����ϱ��ϱ�
        if(!dobj.getRetObject("SEL1").getRecord().get("DISP_DAY").equals(""))
        {
            dobj  = CALLsel_day_SEL3(Conn, dobj);           //  ��������
            dobj  = CALLsel_day_SEL2( dobj);        //  �������
        }
        else
        {
            dobj  = CALLsel_day_SEL4(Conn, dobj);           //  ��������
            dobj  = CALLsel_day_SEL2( dobj);        //  �������
        }
        return dobj;
    }
    // MAX ����ϱ��ϱ�
    // �ش����� ��ϵǾ� �ִ� ��¥�� MAX���� ���Ѵ�
    public DOBJ CALLsel_day_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_day_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_day_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(DISP_DAY)  DISP_DAY  FROM  GIBU.TBRA_NOLEV_DISP_LIST  WHERE  DISP_DAY  LIKE  :YRMN||'%' ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    // ��������
    public DOBJ CALLsel_day_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_day_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_day_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DISP_DAY = dobj.getRetObject("SEL1").getRecord().get("DISP_DAY");   //�߼� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR((TO_DATE(:DISP_DAY,'YYYYMMDD')  +  1  )  ,  'YYYYMMDD')  START_DAY  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("DISP_DAY", DISP_DAY);               //�߼� ����
        return sobj;
    }
    // �������
    public DOBJ CALLsel_day_SEL2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL3, SEL4","START_DAY");
        rvobj.setName("SEL2") ;
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ��������
    public DOBJ CALLsel_day_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_day_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_day_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :YRMN  ||  '01'  AS  START_DAY  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    //##**$$sel_day
    //##**$$nolev_disp_rept
    /* * ���α׷��� : bra04_p09
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/01
    * ����    :
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLnolev_disp_rept(DOBJ dobj)
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
            dobj  = CALLnolev_disp_rept_SEL1(Conn, dobj);           //  �ܺι߼۴�� �Ա���Ȳ
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
    public DOBJ CTLnolev_disp_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnolev_disp_rept_SEL1(Conn, dobj);           //  �ܺι߼۴�� �Ա���Ȳ
        return dobj;
    }
    // �ܺι߼۴�� �Ա���Ȳ
    public DOBJ CALLnolev_disp_rept_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLnolev_disp_rept_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnolev_disp_rept_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TD.UPSO_CD  ,  TD.UPSO_NM  ,  TD.MNGEMSTR_NM  ,  TE.GRADNM  BSTYP_NM  ,  TA.DEMD_AMT+TA.ADDT_AMT  DISP_AMT  ,  TB.START_YRMN  ,  TB.END_YRMN  ,  TB.REPT_AMT  FROM  GIBU.TBRA_NOLEV_DISP_LIST  TA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  SUM(USE_AMT  +  NVL(BALANCE,  0))  REPT_AMT  ,  MAX(REPT_DAY)  REPT_DAY  ,  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  FROM  GIBU.TBRA_NOTE  A  ,  GIBU.TBRA_UPSO  B  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  REPT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  )  C  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  C.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  A.REPT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.USE_AMT  <>  0  GROUP  BY  A.UPSO_CD  )  TB  ,  GIBU.TBRA_UPSO  TD  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZC.GRADNM  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TE  WHERE  TA.DISP_DAY  BETWEEN  :YRMN||'01'   \n";
        query +=" AND  :YRMN||'31'   \n";
        query +=" AND  TD.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TB.UPSO_CD(+)  =  TA.UPSO_CD   \n";
        query +=" AND  TD.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TE.UPSO_CD(+)  =  TA.UPSO_CD   \n";
        query +=" AND  TRIM(TE.BSTYP_CD)  =  DECODE(:BSTYP_CD,  NULL,  TRIM(TE.BSTYP_CD),  :BSTYP_CD)  ORDER  BY  TD.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    //##**$$nolev_disp_rept
    //##**$$end
}
