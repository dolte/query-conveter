
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_p01
{
    public bra01_p01()
    {
    }
    //##**$$sel_upso_doc
    /*
    */
    public DOBJ CTLsel_upso_doc(DOBJ dobj)
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
            dobj  = CALLsel_upso_doc_SEL1(Conn, dobj);           //  ���Ҽ������� ��ȸ
            dobj  = CALLsel_upso_doc_SEL2(Conn, dobj);           //  ������Ͽ��� ��ȸ(pivot)
            dobj  = CALLsel_upso_doc_SEL3(Conn, dobj);           //  ������Ͽ��� ��ȸ(listagg)
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
    public DOBJ CTLsel_upso_doc( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_upso_doc_SEL1(Conn, dobj);           //  ���Ҽ������� ��ȸ
        dobj  = CALLsel_upso_doc_SEL2(Conn, dobj);           //  ������Ͽ��� ��ȸ(pivot)
        dobj  = CALLsel_upso_doc_SEL3(Conn, dobj);           //  ������Ͽ��� ��ȸ(listagg)
        return dobj;
    }
    // ���Ҽ������� ��ȸ
    public DOBJ CALLsel_upso_doc_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_upso_doc_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_upso_doc_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  FILE_TYPE  ,  FIDU.GET_CODE_NM('00198',  FILE_TYPE)  AS  FILE_TYPE_NM  ,  FILE_NM  ,  SVR_FILE_NM  ,  SVR_FILE_ROUT  ,  INSPRES_ID  ,  FIDU.GET_STAFF_NM(INSPRES_ID)  AS  INSPRES_NM  ,  INS_DATE  ,  MODPRES_ID  ,  FIDU.GET_STAFF_NM(MODPRES_ID)  AS  MODPRES_NM  ,  MOD_DATE  FROM  GIBU.TBRA_UPSO_DOC_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������Ͽ��� ��ȸ(pivot)
    public DOBJ CALLsel_upso_doc_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_upso_doc_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_upso_doc_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LISTAGG(B.CODE_NM  ||  ','  ||  (CASE  WHEN  A.FILE_NM  IS  NULL  THEN  'N'  ELSE  'Y'  END),  ',')  WITHIN  GROUP  (ORDER  BY  B.CODE_CD)  AS  RESULT  FROM  GIBU.TBRA_UPSO_DOC_ATTCH  A  ,  FIDU.TENV_CODE  B  WHERE  B.HIGH_CD  =  '00198'   \n";
        query +=" AND  B.CODE_ETC  =  'DI'   \n";
        query +=" AND  B.CODE_CD  =  A.FILE_TYPE(+)   \n";
        query +=" AND  A.UPSO_CD(+)  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������Ͽ��� ��ȸ(listagg)
    public DOBJ CALLsel_upso_doc_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_upso_doc_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_upso_doc_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FIDU.GET_CODE_NM('00198',  EXTRACTVALUE(FILE_TYPE_XML,  '/PivotSet/item[1]/column[1]'))  AS  TYPE1  ,  DECODE(EXTRACTVALUE(FILE_TYPE_XML,  '/PivotSet/item[1]/column[2]'),  0,  'N',  'Y')  AS  FILE_YN1  ,  FIDU.GET_CODE_NM('00198',  EXTRACTVALUE(FILE_TYPE_XML,  '/PivotSet/item[2]/column[1]'))  AS  TYPE2  ,  DECODE(EXTRACTVALUE(FILE_TYPE_XML,  '/PivotSet/item[2]/column[2]'),  0,  'N',  'Y')  AS  FILE_YN2  ,  FIDU.GET_CODE_NM('00198',  EXTRACTVALUE(FILE_TYPE_XML,  '/PivotSet/item[3]/column[1]'))  AS  TYPE3  ,  DECODE(EXTRACTVALUE(FILE_TYPE_XML,  '/PivotSet/item[3]/column[2]'),  0,  'N',  'Y')  AS  FILE_YN3  FROM  (   \n";
        query +=" SELECT  A.FILE_NM  ,  A.FILE_TYPE  FROM  GIBU.TBRA_UPSO_DOC_ATTCH  A  ,  FIDU.TENV_CODE  B  WHERE  B.HIGH_CD  =  '00198'   \n";
        query +=" AND  B.CODE_ETC  =  'DI'   \n";
        query +=" AND  B.CODE_CD  =  A.FILE_TYPE(+)   \n";
        query +=" AND  A.UPSO_CD(+)  =  :UPSO_CD  )  PIVOT  XML  (  COUNT(FILE_NM)  FOR  FILE_TYPE  IN   \n";
        query +=" (SELECT  CODE_CD  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00198'   \n";
        query +=" AND  CODE_ETC  =  'DI')) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_upso_doc
    //##**$$mng_upso_doc
    /*
    */
    public DOBJ CTLmng_upso_doc(DOBJ dobj)
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
            dobj  = CALLmng_upso_doc_SEL_FILE(Conn, dobj);           //  ���������
            dobj  = CALLmng_upso_doc_MIUD1(Conn, dobj);           //  ���Ҽ��� ÷������
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
    public DOBJ CTLmng_upso_doc( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_upso_doc_SEL_FILE(Conn, dobj);           //  ���������
        dobj  = CALLmng_upso_doc_MIUD1(Conn, dobj);           //  ���Ҽ��� ÷������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ���������
    public DOBJ CALLmng_upso_doc_SEL_FILE(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL_FILE");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL_FILE");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_upso_doc_SEL_FILE(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL_FILE");
        rvobj.Println("SEL_FILE");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_upso_doc_SEL_FILE(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '/upload_file/GIBU/UPSO/'  ||  :BRAN_CD  AS  DFILEPATH  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���Ҽ��� ÷������
    public DOBJ CALLmng_upso_doc_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S1");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_upso_doc_SEL25(Conn, dobj);           //  ��������ο� ���ϸ�
                dobj  = CALLmng_upso_doc_FUT26( dobj);        //  �����̵�
                dobj  = CALLmng_upso_doc_FUT27( dobj);        //  �����̸��ٲٱ�
                dobj  = CALLmng_upso_doc_INS31(Conn, dobj);           //  ���Ͼ��ε���������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_upso_doc_SEL31(Conn, dobj);           //  �������� ��ȸ(�������)
                dobj  = CALLmng_upso_doc_FUT32( dobj);        //  ���ϻ���
                dobj  = CALLmng_upso_doc_SEL33(Conn, dobj);           //  ��������ο� ���ϸ�
                dobj  = CALLmng_upso_doc_FUT34( dobj);        //  �����̵�
                dobj  = CALLmng_upso_doc_FUT35( dobj);        //  �����̸��ٲٱ�
                dobj  = CALLmng_upso_doc_UPD36(Conn, dobj);           //  ���Ͼ��ε����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_upso_doc_FUT39( dobj);        //  ���ϻ���
                dobj  = CALLmng_upso_doc_DEL40(Conn, dobj);           //  ��������
            }
        }
        return dobj;
    }
    // ���ϻ���
    public DOBJ CALLmng_upso_doc_FUT39(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT39");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT39");
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            wutil.delete(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString() );
        }
        dvobj.setName("FUT39") ;
        dvobj.Println("FUT39");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �������� ��ȸ(�������)
    public DOBJ CALLmng_upso_doc_SEL31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL31");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL31");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_upso_doc_SEL31(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_upso_doc_SEL31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   FILE_TYPE = dobj.getRetObject("R").getRecord().get("FILE_TYPE");   //���� Ÿ��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SVR_FILE_NM  ,  SVR_FILE_ROUT  FROM  GIBU.TBRA_UPSO_DOC_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  FILE_TYPE  =  :FILE_TYPE ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("FILE_TYPE", FILE_TYPE);               //���� Ÿ��
        return sobj;
    }
    // ��������ο� ���ϸ�
    public DOBJ CALLmng_upso_doc_SEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL25");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL25");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_upso_doc_SEL25(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL25");
        rvobj.Println("SEL25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_upso_doc_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   FILE_TYPE = dobj.getRetObject("R").getRecord().get("FILE_TYPE");   //���� Ÿ��
        String   FILE_NM = dobj.getRetObject("R").getRecord().get("FILE_NM");   //���� ��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  ||  :UPSO_CD  ||  '-'  ||  :FILE_TYPE  ||  '-'  ||  TO_CHAR(SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR(:FILE_NM,  INSTR(:FILE_NM,  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("FILE_TYPE", FILE_TYPE);               //���� Ÿ��
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        return sobj;
    }
    // ��������
    public DOBJ CALLmng_upso_doc_DEL40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL40");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL40");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_upso_doc_DEL40(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL40") ;
        rvobj.Println("DEL40");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_upso_doc_DEL40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FILE_TYPE = dvobj.getRecord().get("FILE_TYPE");   //���� Ÿ��
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_DOC_ATTCH  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and FILE_TYPE=:FILE_TYPE";
        sobj.setSql(query);
        sobj.setString("FILE_TYPE", FILE_TYPE);               //���� Ÿ��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ϻ���
    public DOBJ CALLmng_upso_doc_FUT32(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT32");
        VOBJ dvobj = dobj.getRetObject("SEL31");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT32");
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            wutil.delete(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString() );
        }
        dvobj.setName("FUT32") ;
        dvobj.Println("FUT32");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �����̵�
    public DOBJ CALLmng_upso_doc_FUT26(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT26");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT26");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT26") ;
        dvobj.Println("FUT26");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �����̸��ٲٱ�
    public DOBJ CALLmng_upso_doc_FUT27(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT27");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT27");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dobj.getRetObject("SEL25").getRecord().get("DFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.rename(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILENAME").toString());
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT27") ;
        dvobj.Println("FUT27");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ��������ο� ���ϸ�
    public DOBJ CALLmng_upso_doc_SEL33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL33");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL33");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_upso_doc_SEL33(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL33");
        rvobj.Println("SEL33");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_upso_doc_SEL33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   FILE_TYPE = dobj.getRetObject("R").getRecord().get("FILE_TYPE");   //���� Ÿ��
        String   FILE_NM = dobj.getRetObject("R").getRecord().get("FILE_NM");   //���� ��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  ||  :UPSO_CD  ||  '-'  ||  :FILE_TYPE  ||  '-'  ||  TO_CHAR(SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR(:FILE_NM,  INSTR(:FILE_NM,  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("FILE_TYPE", FILE_TYPE);               //���� Ÿ��
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        return sobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLmng_upso_doc_INS31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS31");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS31");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_upso_doc_INS31(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS31") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_upso_doc_INS31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   FILE_NM = dvobj.getRecord().get("FILE_NM");   //���� ��
        String   FILE_TYPE = dvobj.getRecord().get("FILE_TYPE");   //���� Ÿ��
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   SVR_FILE_NM = dobj.getRetObject("SEL25").getRecord().get("DFILENAME");   //���� ���� ��
        String   SVR_FILE_ROUT = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");   //���� ���� ���
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_DOC_ATTCH (INS_DATE, INSPRES_ID, UPSO_CD, SVR_FILE_ROUT, SVR_FILE_NM, FILE_TYPE, FILE_NM)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :UPSO_CD , :SVR_FILE_ROUT , :SVR_FILE_NM , :FILE_TYPE , :FILE_NM )";
        sobj.setSql(query);
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        sobj.setString("FILE_TYPE", FILE_TYPE);               //���� Ÿ��
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("SVR_FILE_NM", SVR_FILE_NM);               //���� ���� ��
        sobj.setString("SVR_FILE_ROUT", SVR_FILE_ROUT);               //���� ���� ���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����̵�
    public DOBJ CALLmng_upso_doc_FUT34(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT34");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT34");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT34") ;
        dvobj.Println("FUT34");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �����̸��ٲٱ�
    public DOBJ CALLmng_upso_doc_FUT35(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT35");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT35");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dobj.getRetObject("SEL33").getRecord().get("DFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.rename(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILENAME").toString());
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT35") ;
        dvobj.Println("FUT35");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε����
    public DOBJ CALLmng_upso_doc_UPD36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD36");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD36");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_upso_doc_UPD36(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD36") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_upso_doc_UPD36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   FILE_NM = dvobj.getRecord().get("FILE_NM");   //���� ��
        String   FILE_TYPE = dvobj.getRecord().get("FILE_TYPE");   //���� Ÿ��
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   SVR_FILE_NM = dobj.getRetObject("SEL33").getRecord().get("DFILENAME");   //���� ���� ��
        String   SVR_FILE_ROUT = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");   //���� ���� ���
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_DOC_ATTCH  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=SYSDATE , SVR_FILE_ROUT=:SVR_FILE_ROUT , SVR_FILE_NM=:SVR_FILE_NM , FILE_NM=:FILE_NM  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and FILE_TYPE=:FILE_TYPE";
        sobj.setSql(query);
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        sobj.setString("FILE_TYPE", FILE_TYPE);               //���� Ÿ��
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("SVR_FILE_NM", SVR_FILE_NM);               //���� ���� ��
        sobj.setString("SVR_FILE_ROUT", SVR_FILE_ROUT);               //���� ���� ���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$mng_upso_doc
    //##**$$end
}
