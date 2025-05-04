
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_p01
{
    public bra05_p01()
    {
    }
    //##**$$sel_accu_print
    /*
    */
    public DOBJ CTLsel_accu_print(DOBJ dobj)
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
            dobj  = CALLsel_accu_print_SEL1(Conn, dobj);           //  ������������ ��ϵ� ����Ʈ�� ��ȸ
            dobj  = CALLsel_accu_print_MPD3(Conn, dobj);           //  ÷�����ϻ��� �б�
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
    public DOBJ CTLsel_accu_print( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_accu_print_SEL1(Conn, dobj);           //  ������������ ��ϵ� ����Ʈ�� ��ȸ
        dobj  = CALLsel_accu_print_MPD3(Conn, dobj);           //  ÷�����ϻ��� �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ������������ ��ϵ� ����Ʈ�� ��ȸ
    public DOBJ CALLsel_accu_print_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_accu_print_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_accu_print_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BRAN_CD  ,  GIBU.GET_BRAN_NM(B.BRAN_CD)  AS  BRAN_NM  ,  A.ACCU_DAY  ,  A.ACCU_NUM  ,  A.ACCU_BRAN  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.ACCU_GBN  ,  A.PLCST_CD  ,  A.EVT_NUM  ,  A.REQ_DAY  ,  NVL(A.REQ_ORG_AMT,  0)  +  NVL(A.REQ_ADDT_AMT,  0)  AS  TOT_AMT  ,  A.REQ_ORG_AMT  ,  A.REQ_ADDT_AMT  ,  B.STAFF_CD  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  AS  STAFF_NM  ,   \n";
        query +=" (SELECT  FILE_TEMPNM  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  A.ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  A.ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  A.ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  'ADVT')  AS  ADVT_FILE  ,   \n";
        query +=" (SELECT  FILE_ROUT  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  A.ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  A.ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  A.ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  'ADVT')  AS  ADVT_ROUT  ,   \n";
        query +=" (SELECT  FILE_TEMPNM  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  A.ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  A.ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  A.ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  'BPAP')  AS  BPAP_FILE  ,   \n";
        query +=" (SELECT  FILE_ROUT  FROM  GIBU.TBRA_ACCU_ATTCH  WHERE  ACCU_DAY  =  A.ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  A.ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  A.ACCU_BRAN   \n";
        query +=" AND  FILE_TYPE  =  'BPAP')  AS  BPAP_ROUT  ,  A.PROD_CD  FROM  GIBU.TBRA_ACCU  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.ACCU_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.STAFF_CD  =  NVL(:STAFF_CD,  B.STAFF_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    // ÷�����ϻ��� �б�
    public DOBJ CALLsel_accu_print_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("SEL1");         //������������ ��ϵ� ����Ʈ�� ��ȸ���� ������Ų OBJECT�Դϴ�.(CALLsel_accu_print_SEL1)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(   dvobj.getRecord().get("ADVT_FILE").length()  > 0)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsel_accu_print_FUT6( dobj);        //  ����������
                if(   dobj.getRetObject("R").getRecord().get("BPAP_FILE").length() > 0)
                {
                    dobj  = CALLsel_accu_print_FUT8( dobj);        //  �ְ�����
                }
            }
            else if(   dvobj.getRecord().get("BPAP_FILE").length()  > 0)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsel_accu_print_FUT9( dobj);        //  �ְ�����
                if(   dobj.getRetObject("R").getRecord().get("ADVT_FILE").length() > 0)
                {
                    dobj  = CALLsel_accu_print_FUT11( dobj);        //  ����������
                }
            }
        }
        return dobj;
    }
    // ����������
    public DOBJ CALLsel_accu_print_FUT6(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT6");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT6");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("ADVT_FILE");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/file/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("ADVT_FILE");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("ADVT_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.copy(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),record.get("DFILENAME").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT6") ;
        dvobj.Println("FUT6");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �ְ�����
    public DOBJ CALLsel_accu_print_FUT9(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT9");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT9");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("BPAP_FILE");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/file/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("BPAP_FILE");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("BPAP_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.copy(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),record.get("DFILENAME").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT9") ;
        dvobj.Println("FUT9");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �ְ�����
    public DOBJ CALLsel_accu_print_FUT8(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT8");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT8");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("BPAP_FILE");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/file/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("BPAP_FILE");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("BPAP_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.copy(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),record.get("DFILENAME").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT8") ;
        dvobj.Println("FUT8");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ����������
    public DOBJ CALLsel_accu_print_FUT11(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT11");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT11");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("ADVT_FILE");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/file/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("ADVT_FILE");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("ADVT_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.copy(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),record.get("DFILENAME").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT11") ;
        dvobj.Println("FUT11");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    //##**$$sel_accu_print
    //##**$$end
}
