
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s11
{
    public bra03_s11()
    {
    }
    //##**$$search_info
    /*
    */
    public DOBJ CTLsearch_info(DOBJ dobj)
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
            dobj  = CALLsearch_info_SEL1(Conn, dobj);           //  �������� ��ȸ
            dobj  = CALLsearch_info_SEL2(Conn, dobj);           //  �ڵ���ü �������� ��ȸ
            dobj  = CALLsearch_info_SEL3(Conn, dobj);           //  �ڵ���ü ��û�̷�
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
    public DOBJ CTLsearch_info( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_info_SEL1(Conn, dobj);           //  �������� ��ȸ
        dobj  = CALLsearch_info_SEL2(Conn, dobj);           //  �ڵ���ü �������� ��ȸ
        dobj  = CALLsearch_info_SEL3(Conn, dobj);           //  �ڵ���ü ��û�̷�
        return dobj;
    }
    // �������� ��ȸ
    public DOBJ CALLsearch_info_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_info_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_info_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  AS  BRAN_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD  ||  XB.UPSO_GRAD  AS  GRAD  ,  XB.GRADNM  ,  XA.STAFF_CD  ,  XC.HAN_NM  AS  STAFF_NM  ,  XB.MONPRNCFEE  ,  XA.UPSO_PHON  ,  XA.MNGEMSTR_NM  ,  XA.MNGEMSTR_RESINUM  ,  SUBSTR(REPLACE(XA.MNGEMSTR_HPNUM,  '-',  ''),  1,  11)  AS  MNGEMSTR_HPNUM  ,  XA.PERMMSTR_NM  ,  XA.PERMMSTR_RESINUM  ,  SUBSTR(REPLACE(XA.PERMMSTR_HPNUM,  '-',  ''),  1,  11)  AS  PERMMSTR_HPNUM  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,'',  '',',  '  ||  XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  AS  ADDR  ,  XA.CLIENT_NUM  ,  XA.CLSBS_YRMN  ||  '01'  AS  CLSBS_YRMN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  AS  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  INSA.TINS_MST01  XC  ,  INSA.TCPM_DEPT  XD  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XD.GIBU  =  XA.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �ڵ���ü �������� ��ȸ
    public DOBJ CALLsearch_info_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_info_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_info_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  RECEPTION_GBN  ,  AUTO_NUM  ,  BANK_CD  ,  (   \n";
        query +=" SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  USE_YN  =  'Y'   \n";
        query +=" AND  BANK_CD  =  SUBSTR(A.BANK_CD,  1,3)  )  AS  BANK_NM  ,  AUTO_ACCNNUM  ,  PAYPRES_NM  ,  RESINUM  ,  TERM_YN  ,  DECODE(TERM_YN,  'Y',  '�ڵ���ü  ����',  'N','�ڵ���ü  ��')  AS  TERM_GBN  ,  DECODE(CARD_GBN,  'WIN',  '�Ｚī��','LGC',  '����ī��','AMX','�Ե�ī��',  CARD_GBN)  AS  CARD_GBN  ,  EXPIRE_DAY  ,  CARD_NUM  ,  BIOWN_INSNUM  FROM  GIBU.TBRA_UPSO_AUTO  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  AUTO_NUM  =  (   \n";
        query +=" SELECT  MAX(AUTO_NUM)  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  :UPSO_CD  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �ڵ���ü ��û�̷�
    // ��ȸ, ����, �ݰ�� ��û�� ��α��. 2012�� 7���� ����
    public DOBJ CALLsearch_info_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_info_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_info_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.SEQ  ,  A.APPLICATION_GBN  ,  DECODE  (A.RECEPTION_GBN,  '7',  DECODE(CARD_GBN,  'WIN',  '�Ｚī��','LGC',  '����ī��','AMX','�Ե�ī��',  CARD_GBN),   \n";
        query +=" (SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  USE_YN  =  'Y'   \n";
        query +=" AND  BANK_CD  =  A.BANK_CD))  AS  BANK_NM  ,  DECODE  (A.RECEPTION_GBN,  '7',A.CARD_NUM,A.AUTO_ACCNNUM)  AS  AUTO_ACCNNUM  ,  A.RESINUM  ,  A.PAYPRES_NM  ,  A.RELATION  ,  A.PHON_NUM  ,  A.APPTN_DAY  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  REMAK  FROM  GIBU.TBRA_UPSO_AUTO_APPLICATION  A  WHERE  A.UPSO_CD  =  :UPSO_CD  ORDER  BY  A.INS_DATE ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$search_info
    //##**$$applicaiton_fileup
    /*
    */
    public DOBJ CTLapplicaiton_fileup(DOBJ dobj)
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
            dobj  = CALLapplicaiton_fileup_DEL10(Conn, dobj);           //  ���� ÷������ ����
            dobj  = CALLapplicaiton_fileup_FUT1( dobj);        //  ���Ϲ���
            dobj  = CALLapplicaiton_fileup_CVT2( dobj);        //  ���Ϲ����̵�����
            dobj  = CALLapplicaiton_fileup_INS5(Conn, dobj);           //  ���Ͼ��ε���������
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
    public DOBJ CTLapplicaiton_fileup( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLapplicaiton_fileup_DEL10(Conn, dobj);           //  ���� ÷������ ����
        dobj  = CALLapplicaiton_fileup_FUT1( dobj);        //  ���Ϲ���
        dobj  = CALLapplicaiton_fileup_CVT2( dobj);        //  ���Ϲ����̵�����
        dobj  = CALLapplicaiton_fileup_INS5(Conn, dobj);           //  ���Ͼ��ε���������
        return dobj;
    }
    // ���� ÷������ ����
    public DOBJ CALLapplicaiton_fileup_DEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL10");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLapplicaiton_fileup_DEL10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLapplicaiton_fileup_DEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���Ϲ���
    public DOBJ CALLapplicaiton_fileup_FUT1(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT1");
        VOBJ dvobj = dobj.getRetObject("S");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT1");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT1") ;
        dvobj.setRetcode(1);
        dvobj.Println("FUT1");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ����̵�����
    public DOBJ CALLapplicaiton_fileup_CVT2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S");        //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("CVT2");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT2") ;
        dvobj.setRetcode(1);
        dvobj.Println("CVT2");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLapplicaiton_fileup_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binFILES=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("S").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("FILES", binFILES);
        }
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLapplicaiton_fileup_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLapplicaiton_fileup_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //����
        String   FILE_NM = dobj.getRetObject("S").getRecord().get("UPFILENAME");   //���ϸ�
        String   FILE_ROUT = dobj.getRetObject("CVT2").getRecord().get("DFILEPATH");   //���ϰ��
        double   FILE_SIZE = dobj.getRetObject("S").getRecord().getDouble("FILESIZE");   //���� ũ��
        String   FILE_TEMPNM = dobj.getRetObject("S").getRecord().get("UNIFILENAME");   //��ȯ ���ϸ�
        double   SEQ = dobj.getRetObject("S1").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("S1").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_DOC_ATTCH (FILE_SIZE, FILE_TEMPNM, FILE_ROUT, UPSO_CD, SEQ, FILE_NM, FILES)  \n";
        query +=" values(:FILE_SIZE , :FILE_TEMPNM , :FILE_ROUT , :UPSO_CD , :SEQ , :FILE_NM , :FILES )";
        sobj.setSql(query);
        sobj.setBlob("FILES", FILES);               //����
        sobj.setString("FILE_NM", FILE_NM);               //���ϸ�
        sobj.setString("FILE_ROUT", FILE_ROUT);               //���ϰ��
        sobj.setDouble("FILE_SIZE", FILE_SIZE);               //���� ũ��
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //��ȯ ���ϸ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$applicaiton_fileup
    //##**$$save
    /*
    */
    public DOBJ CTLsave(DOBJ dobj)
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
            dobj  = CALLsave_MIUD1(Conn, dobj);           //  ��û������
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
    public DOBJ CTLsave( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_MIUD1(Conn, dobj);           //  ��û������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ��û������
    public DOBJ CALLsave_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLsave_SEL9(Conn, dobj);           //  seq ȹ��
                dobj  = CALLsave_INS5(Conn, dobj);           //  �űԵ��
                dobj  = CALLsave_SEL7(Conn, dobj);           //  ÷�������seq��ȯ
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_UPD6(Conn, dobj);           //  ����
                dobj  = CALLsave_SEL8(Conn, dobj);           //  ÷�������seq��ȯ
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_DEL11(Conn, dobj);           //  ����
            }
        }
        return dobj;
    }
    // ����
    public DOBJ CALLsave_DEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_DEL11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_DEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ����
    public DOBJ CALLsave_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String PAYPRES_NM ="";        //������ ��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //ī�� ��ȣ
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   RECEPTION_GBN = dvobj.getRecord().get("RECEPTION_GBN");   //����ó
        String   AUTO_ACCNNUM = dvobj.getRecord().get("AUTO_ACCNNUM");   //��� ���¹�ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   CARD_GBN = dvobj.getRecord().get("CARD_GBN");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   RELATION = dvobj.getRecord().get("RELATION");   //���
        String   BIOWN_INSNUM = dvobj.getRecord().get("BIOWN_INSNUM");   //����� ��Ϲ�ȣ
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   PHON_NUM = dvobj.getRecord().get("PHON_NUM");   //��ȭ ��ȣ
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //���� ��
        String   APPLICATION_GBN = dvobj.getRecord().get("APPLICATION_GBN");   //��û ����
        String   PAYPRES_NM_1 = dobj.getRetObject("R").getRecord().get("PAYPRES_NM");   //������ ��
        String   BANK_CD = dvobj.getRecord().get("BANK_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" set INSPRES_ID=:INSPRES_ID , BANK_CD=:BANK_CD , PAYPRES_NM=REPLACE(REPLACE(:PAYPRES_NM_1, CHR(13), ''), CHR(10), '') , APPLICATION_GBN=:APPLICATION_GBN , EXPIRE_DAY=:EXPIRE_DAY , PHON_NUM=:PHON_NUM , BIOWN_INSNUM=:BIOWN_INSNUM , RELATION=:RELATION , CARD_GBN=:CARD_GBN , STAFF_CD=:STAFF_CD , APPTN_DAY=:APPTN_DAY , AUTO_ACCNNUM=:AUTO_ACCNNUM , RECEPTION_GBN=:RECEPTION_GBN , RESINUM=:RESINUM , CARD_NUM=:CARD_NUM , REMAK=:REMAK  \n";
        query +=" where SEQ=:SEQ  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //����ó
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //��� ���¹�ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("RELATION", RELATION);               //���
        sobj.setString("BIOWN_INSNUM", BIOWN_INSNUM);               //����� ��Ϲ�ȣ
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("PHON_NUM", PHON_NUM);               //��ȭ ��ȣ
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //���� ��
        sobj.setString("APPLICATION_GBN", APPLICATION_GBN);               //��û ����
        sobj.setString("PAYPRES_NM_1", PAYPRES_NM_1);               //������ ��
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // seq ȹ��
    public DOBJ CALLsave_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsave_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  SEQ  FROM  GIBU.TBRA_UPSO_AUTO_APPLICATION  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �űԵ��
    public DOBJ CALLsave_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String PAYPRES_NM ="";        //������ ��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //ī�� ��ȣ
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   RECEPTION_GBN = dvobj.getRecord().get("RECEPTION_GBN");   //����ó
        String   AUTO_ACCNNUM = dvobj.getRecord().get("AUTO_ACCNNUM");   //��� ���¹�ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   CARD_GBN = dvobj.getRecord().get("CARD_GBN");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   RELATION = dvobj.getRecord().get("RELATION");   //���
        String   BIOWN_INSNUM = dvobj.getRecord().get("BIOWN_INSNUM");   //����� ��Ϲ�ȣ
        String   PHON_NUM = dvobj.getRecord().get("PHON_NUM");   //��ȭ ��ȣ
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //���� ��
        String   APPLICATION_GBN = dvobj.getRecord().get("APPLICATION_GBN");   //��û ����
        String   PAYPRES_NM_1 = dobj.getRetObject("R").getRecord().get("PAYPRES_NM");   //������ ��
        String   BANK_CD = dvobj.getRecord().get("BANK_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   SEQ = dobj.getRetObject("SEL9").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_APPLICATION (INSPRES_ID, BANK_CD, PAYPRES_NM, APPLICATION_GBN, EXPIRE_DAY, PHON_NUM, SEQ, BIOWN_INSNUM, INS_DATE, RELATION, CARD_GBN, STAFF_CD, APPTN_DAY, UPSO_CD, AUTO_ACCNNUM, RECEPTION_GBN, RESINUM, CARD_NUM, REMAK)  \n";
        query +=" values(:INSPRES_ID , :BANK_CD , REPLACE(REPLACE(:PAYPRES_NM_1, CHR(13), ''), CHR(10), ''), :APPLICATION_GBN , :EXPIRE_DAY , :PHON_NUM , :SEQ , :BIOWN_INSNUM , SYSDATE, :RELATION , :CARD_GBN , :STAFF_CD , :APPTN_DAY , :UPSO_CD , :AUTO_ACCNNUM , :RECEPTION_GBN , :RESINUM , :CARD_NUM , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //����ó
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //��� ���¹�ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("RELATION", RELATION);               //���
        sobj.setString("BIOWN_INSNUM", BIOWN_INSNUM);               //����� ��Ϲ�ȣ
        sobj.setString("PHON_NUM", PHON_NUM);               //��ȭ ��ȣ
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //���� ��
        sobj.setString("APPLICATION_GBN", APPLICATION_GBN);               //��û ����
        sobj.setString("PAYPRES_NM_1", PAYPRES_NM_1);               //������ ��
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ÷�������seq��ȯ
    public DOBJ CALLsave_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsave_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD,  :SEQ  AS  SEQ  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ÷�������seq��ȯ
    public DOBJ CALLsave_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsave_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("SEL9").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD,  :SEQ  AS  SEQ  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    //##**$$save
    //##**$$search_detail
    /*
    */
    public DOBJ CTLsearch_detail(DOBJ dobj)
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
            dobj  = CALLsearch_detail_SEL1(Conn, dobj);           //  ��û�� ����ȸ
            dobj  = CALLsearch_detail_SEL2(Conn, dobj);           //  ����ڵ�ȹ��
            dobj  = CALLsearch_detail_SEL3(Conn, dobj);           //  ÷������ ���
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
    public DOBJ CTLsearch_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_detail_SEL1(Conn, dobj);           //  ��û�� ����ȸ
        dobj  = CALLsearch_detail_SEL2(Conn, dobj);           //  ����ڵ�ȹ��
        dobj  = CALLsearch_detail_SEL3(Conn, dobj);           //  ÷������ ���
        return dobj;
    }
    // ��û�� ����ȸ
    public DOBJ CALLsearch_detail_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_detail_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_detail_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("S").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.SEQ  ,  A.APPLICATION_GBN  ,  A.APPTN_DAY  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  A.BANK_CD  ,  A.AUTO_ACCNNUM  ,  A.PAYPRES_NM  ,  A.RESINUM  ,  SUBSTR(A.RESINUM,  1,6)  AS  RESINUM_1  ,  SUBSTR(A.RESINUM,  7)  AS  RESINUM_2  ,  A.RELATION  ,  A.PHON_NUM  ,  A.REMAK  ,  A.STAFF_CD  ,  A.INSPRES_ID  ,   \n";
        query +=" (SELECT  HAN_NM  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  A.INSPRES_ID)  AS  INSPRES_NM  ,  A.CONFIRM_ID  ,   \n";
        query +=" (SELECT  HAN_NM  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  A.CONFIRM_ID)  AS  CONFIRM_NM  ,  A.RECEPTION_GBN  ,  B.FILE_ROUT  ,  B.FILE_TEMPNM  ,  A.CARD_GBN  ,  A.EXPIRE_DAY  ,  A.CARD_NUM  ,  A.BIOWN_INSNUM  FROM  GIBU.TBRA_UPSO_AUTO_APPLICATION  A,  GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.SEQ  =  :SEQ   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.SEQ  =  B.SEQ(+)  ORDER  BY  SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ����ڵ�ȹ��
    public DOBJ CALLsearch_detail_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_detail_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_detail_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("SEL1").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.STAFF_NUM  STAFF_CD  ,  B.HAN_NM  STAFF_NM  ,  B.ETCOM_DAY  FROM  INSA.TINS_MST01  B  WHERE  B.STAFF_NUM  =  :STAFF_CD  UNION   \n";
        query +=" SELECT  B.STAFF_NUM  STAFF_CD  ,  B.HAN_NM  STAFF_NM  ,  B.ETCOM_DAY  FROM  INSA.TCPM_DEPT  A  ,  INSA.TINS_MST01  B  WHERE  A.DEPT_CD  =  B.DEPT_CD   \n";
        query +=" AND  A.GIBU  =:BRAN_CD   \n";
        query +=" AND  B.RETIRE_DAY  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ÷������ ���
    // ÷������ ����� �����´�.
    public DOBJ CALLsearch_detail_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_detail_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        String fullname="";
        rvobj.first();
        while(rvobj.next())
        {
            wutil.makeFileOverwirte( dobj.getRetObject("SEL1").getRecord().get("FILE_ROUT"), dobj.getRetObject("SEL1").getRecord().get("FILE_TEMPNM"),rvobj.getRecord().getBytes("FILES"));
        }
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_detail_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("S").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD,  SEQ,  FILE_NM,  FILE_ROUT,  FILE_TEMPNM,  FILE_SIZE,  FILES  FROM  GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    //##**$$search_detail
    //##**$$search_bank_code
    /*
    */
    public DOBJ CTLsearch_bank_code(DOBJ dobj)
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
            dobj  = CALLsearch_bank_code_SEL1(Conn, dobj);           //  ��ǥ������ȸ
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
    public DOBJ CTLsearch_bank_code( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_bank_code_SEL1(Conn, dobj);           //  ��ǥ������ȸ
        return dobj;
    }
    // ��ǥ������ȸ
    public DOBJ CALLsearch_bank_code_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_bank_code_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_bank_code_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BANK_CD  ,  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  USE_YN  =  'Y'   \n";
        query +=" AND  BANK_CD_YN  =  'Y' ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$search_bank_code
    //##**$$end
}
