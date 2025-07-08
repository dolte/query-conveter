
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac09_s02
{
    public tac09_s02()
    {
    }
    //##**$$fi_end_close
    /* * ���α׷��� : tac09_s03
    * �ۼ��� : �̼���
    * �ۼ��� : 2009/11/30
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLfi_end_close(DOBJ dobj)
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
            dobj  = CALLfi_end_close_SEL4(Conn, dobj);           //  ��ǰ�����ܾ���ȸ
            dobj  = CALLfi_end_close_MPD8(Conn, dobj);           //  ��ǰ�����ܾ�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLfi_end_close_SEL1(Conn, dobj);           //  ��õ������
            dobj  = CALLfi_end_close_INS1(Conn, dobj);           //  1
            dobj  = CALLfi_end_close_UNI4(Conn, dobj);           //  ��������������
            dobj  = CALLfi_end_close_XIUD13(Conn, dobj);           //  0�αݾ� ����
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
    public DOBJ CTLfi_end_close( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLfi_end_close_SEL4(Conn, dobj);           //  ��ǰ�����ܾ���ȸ
        dobj  = CALLfi_end_close_MPD8(Conn, dobj);           //  ��ǰ�����ܾ�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLfi_end_close_SEL1(Conn, dobj);           //  ��õ������
        dobj  = CALLfi_end_close_INS1(Conn, dobj);           //  1
        dobj  = CALLfi_end_close_UNI4(Conn, dobj);           //  ��������������
        dobj  = CALLfi_end_close_XIUD13(Conn, dobj);           //  0�αݾ� ����
        return dobj;
    }
    // ��ǰ�����ܾ���ȸ
    // ��ǰ�����ܾ���ȸ
    public DOBJ CALLfi_end_close_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLfi_end_close_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLfi_end_close_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MB_CD,  SUM(BFRMON_REMD)  AS  BFRMON_REMD,  SUM(PREMON_OCC_AMT)  AS  PREMON_OCC_AMT,  SUM(PREMON_RELS_AMT)  AS  PREMON_RELS_AMT,  SUM(BFRMON_REMD)  +  SUM(PREMON_OCC_AMT)  -  SUM(PREMON_RELS_AMT)  AS  PREMON_REMD  FROM(   \n";
        query +=" SELECT  MB_CD,  PREMON_REMD  AS  BFRMON_REMD,  0  AS  PREMON_OCC_AMT,  0  AS  PREMON_RELS_AMT  FROM  FIDU.TTAC_PRODSUSPREMD  WHERE  SUPP_YRMN  =  TO_CHAR(ADD_MONTHS(TO_DATE(:SUPP_YRMN,'YYYYMM'),-1),'YYYYMM')  UNION  ALL   \n";
        query +=" SELECT  ORGAU_MB_CD  AS  MB_CD,  0  AS  BFRMON_REMD,  DISTR_AMT  AS  PREMON_OCC_AMT,  0  AS  PREMON_RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  WRK_YRMN  =  :SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  A.ORGAU_MB_CD  AS  MB_CD,  0  AS  BFRMON_REMD,  0  AS  PREMON_OCC_AMT,  SUM(DISTR_AMT)  AS  PREMON_RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  A  WHERE  A.SUPP_YRMN  =  :SUPP_YRMN   \n";
        query +=" AND  SUPP_GBN  ='Y'  GROUP  BY  A.ORGAU_MB_CD  )  WHERE  1  =  1  GROUP  BY  MB_CD ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    // ��ǰ�����ܾ�
    public DOBJ CALLfi_end_close_MPD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD8");
        VOBJ dvobj = dobj.getRetObject("SEL4");         //��ǰ�����ܾ���ȸ���� ������Ų OBJECT�Դϴ�.(CALLfi_end_close_SEL4)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLfi_end_close_UNI6(Conn, dobj);           //  ��ǰ�����ܾ��Է�
            }
        }
        return dobj;
    }
    // ��ǰ�����ܾ��Է�
    public DOBJ CALLfi_end_close_UNI6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLfi_end_close_UNI6UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLfi_end_close_UNI6INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLfi_end_close_UNI6UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   PREMON_OCC_AMT = dvobj.getRecord().getDouble("PREMON_OCC_AMT");   //��� �߻� �ݾ�
        double   BFRMON_REMD = dobj.getRetObject("R").getRecord().getDouble("BFRMON_REMD");   //�����ܾ�
        String   MB_CD = dobj.getRetObject("R").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        double   PREMON_RELS_AMT = dobj.getRetObject("R").getRecord().getDouble("PREMON_RELS_AMT");   //��� ���� �ݾ�
        double   PREMON_REMD = dobj.getRetObject("R").getRecord().getDouble("PREMON_REMD");   //��� �ܾ�
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_PRODSUSPREMD  \n";
        query +=" set BFRMON_REMD=:BFRMON_REMD , PREMON_REMD=:PREMON_REMD , PREMON_OCC_AMT=:PREMON_OCC_AMT , PREMON_RELS_AMT=:PREMON_RELS_AMT  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setDouble("PREMON_OCC_AMT", PREMON_OCC_AMT);               //��� �߻� �ݾ�
        sobj.setDouble("BFRMON_REMD", BFRMON_REMD);               //�����ܾ�
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setDouble("PREMON_RELS_AMT", PREMON_RELS_AMT);               //��� ���� �ݾ�
        sobj.setDouble("PREMON_REMD", PREMON_REMD);               //��� �ܾ�
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    private SQLObject SQLfi_end_close_UNI6INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   PREMON_OCC_AMT = dvobj.getRecord().getDouble("PREMON_OCC_AMT");   //��� �߻� �ݾ�
        double   BFRMON_REMD = dobj.getRetObject("R").getRecord().getDouble("BFRMON_REMD");   //�����ܾ�
        String   MB_CD = dobj.getRetObject("R").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        double   PREMON_RELS_AMT = dobj.getRetObject("R").getRecord().getDouble("PREMON_RELS_AMT");   //��� ���� �ݾ�
        double   PREMON_REMD = dobj.getRetObject("R").getRecord().getDouble("PREMON_REMD");   //��� �ܾ�
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_PRODSUSPREMD (MB_CD, BFRMON_REMD, PREMON_REMD, PREMON_OCC_AMT, PREMON_RELS_AMT, SUPP_YRMN)  \n";
        query +=" values(:MB_CD , :BFRMON_REMD , :PREMON_REMD , :PREMON_OCC_AMT , :PREMON_RELS_AMT , :SUPP_YRMN )";
        sobj.setSql(query);
        sobj.setDouble("PREMON_OCC_AMT", PREMON_OCC_AMT);               //��� �߻� �ݾ�
        sobj.setDouble("BFRMON_REMD", BFRMON_REMD);               //�����ܾ�
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setDouble("PREMON_RELS_AMT", PREMON_RELS_AMT);               //��� ���� �ݾ�
        sobj.setDouble("PREMON_REMD", PREMON_REMD);               //��� �ܾ�
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    // ��õ������
    public DOBJ CALLfi_end_close_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLfi_end_close_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLfi_end_close_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INS_STAFF = dobj.getRetObject("G").getRecord().get("USERID");   //��� ���
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SS_NUM,  INCOM_GBN,  SUPP_DAY,  RETURN_YRMN,  DUTY_DAYFREQ,  SUPP_TOTAMT,  NEED_EXPS,  INCOM_AMT,  TAXRATE,  INCOMTAX,  CORPTAX,  INHABTAX,  VILL_SPCTAX,  TAX_AMT_TOTAL,  USE_YN,  INS_DAY,  INS_STAFF,  MOD_DAY,  MOD_STAFF,  NM,  ADDR,  FNM,  BIPLC_ADDR,  PHON_NUM,  MOBILE,  BANK_CD,  ACCN_NUM,  RESIDENT_GBN,  HOUSE_CD,  INCOM_GBN_CD,  DETED_ADDR,  POST_NUM,  SUPP_DEPOSITOR,  BIPLC_DETD_ADDR,  BIPLC_POST_NUM,  CHIT_CD,  ACCTN_GBN,  GUBUN_SUCCE,  SUSU,  MEM_NO,  INCOMPRES_GBN,  CASE  WHEN  GUBUN_SUCCE  =  2   \n";
        query +=" AND  RESIDENT_GBN  =  '001'  THEN  INCOM_AMT  -  NEED_EXPS  WHEN  GUBUN_SUCCE  =  2   \n";
        query +=" AND  RESIDENT_GBN  =  '002'  THEN  INCOM_AMT  ELSE  INCOM_AMT  END  AS  INCOME_TAX_AMT,  CASE  WHEN  RESIDENT_GBN  =  '001'   \n";
        query +=" AND  MB_GBN1  <>  'U'  THEN  RESIDENT_GBN  ||  INCOM_GBN  WHEN  RESIDENT_GBN  =  '002'   \n";
        query +=" AND  MB_GBN1  <>  'U'  THEN  RESIDENT_GBN  ||  '001'  WHEN  RESIDENT_GBN  =  '002'   \n";
        query +=" AND  MB_GBN1  =  'U'  THEN  RESIDENT_GBN  ||  '004'  ELSE  INCOM_GBN  ||  '999'  END  AS  INCOME_GBN_D  ,  CASE  WHEN  INCOM_GBN  =  '001'   \n";
        query +=" AND  GUBUN_SUCCE  =  '1'   \n";
        query +=" AND  RESIDENT_GBN  =  '001'  THEN  INCOM_GBN  ||  RESIDENT_GBN  WHEN  INCOM_GBN  =  '002'   \n";
        query +=" AND  GUBUN_SUCCE  =  '2'   \n";
        query +=" AND  RESIDENT_GBN  =  '001'  THEN  INCOM_GBN  ||  RESIDENT_GBN  WHEN  INCOM_GBN  =  '001'   \n";
        query +=" AND  GUBUN_SUCCE  =  '1'   \n";
        query +=" AND  RESIDENT_GBN  =  '002'  THEN  INCOM_GBN  ||  RESIDENT_GBN  WHEN  INCOM_GBN  =  '002'   \n";
        query +=" AND  GUBUN_SUCCE  =  '2'   \n";
        query +=" AND  RESIDENT_GBN  =  '002'  THEN  INCOM_GBN  ||  RESIDENT_GBN  WHEN  INCOM_GBN  =  '004'   \n";
        query +=" AND  RESIDENT_GBN  =  '002'  THEN  INCOM_GBN  ||  RESIDENT_GBN  ELSE  INCOM_GBN  ||  '999'  END  AS  INCOME_GBN_ETC  ,  NAT_CD  FROM   \n";
        query +=" (SELECT  B.INS_NUM  AS  SS_NUM,  CASE  WHEN  A.TRNSF_GBN  =  '1'   \n";
        query +=" AND  B.MB_GBN1  <>  'U'  THEN  '001'  WHEN  A.TRNSF_GBN  =  '2'   \n";
        query +=" AND  B.MB_GBN1  <>  'U'  THEN  '002'  ELSE  '004'  END  AS  INCOM_GBN,  A.SUPP_YRMN||'23'  AS  SUPP_DAY,  A.SUPP_YRMN  AS  RETURN_YRMN,  0  AS  DUTY_DAYFREQ,  A.TOT_REALSUPP_AMT  AS  SUPP_TOTAMT,  D.DEDCT_AMT  AS  NEED_EXPS,  (A.TOT_REALSUPP_AMT  +  A.TOT_DEDCT_AMT)  AS  INCOM_AMT,  E.APPL_TAXRATE  AS  TAXRATE,  E.DEDCT_AMT  AS  INCOMTAX,  0  AS  CORPTAX,  F.DEDCT_AMT  AS  INHABTAX,  0  AS  VILL_SPCTAX,  E.DEDCT_AMT  +  F.DEDCT_AMT  AS  TAX_AMT_TOTAL,  'Y'  AS  USE_YN,  TO_CHAR(SYSDATE,  'YYYYMMDD')  AS  INS_DAY,  :INS_STAFF  AS  INS_STAFF,  TO_CHAR(SYSDATE,  'YYYYMMDD')  AS  MOD_DAY,  '  '  AS  MOD_STAFF,  B.HANMB_NM  AS  NM,  C.ADDR  AS  ADDR,  B.INS_NUM  AS  BIOWN_INSNUM,  B.HANMB_NM  AS  FNM,  C.ADDR  AS  BIPLC_ADDR,  B.PHON_NUM  AS  PHON_NUM,  B.CP_NUM  AS  MOBILE,  B.SUPPBANK_CD  AS  BANK_CD,  B.SUPPACCN_NUM  AS  ACCN_NUM,  CASE  WHEN  B.HOUSE_CD  =  '082'  THEN  '001'  ELSE  '002'  END  AS  RESIDENT_GBN,  B.HOUSE_CD  AS  HOUSE_CD,  '001'  AS  INCOM_GBN_CD,  C.ADDR_DETED  AS  DETED_ADDR,  C.POST_NUM  AS  POST_NUM,  B.HANMB_NM  AS  SUPP_DEPOSITOR,  C.ADDR_DETED  AS  BIPLC_DETD_ADDR,  C.POST_NUM  AS  BIPLC_POST_NUM,  '  '  AS  CHIT_CD,  '001'  AS  ACCTN_GBN,  A.TRNSF_GBN  AS  GUBUN_SUCCE,  D.DEDCT_AMT  AS  SUSU,  A.MB_CD  AS  MEM_NO,  CASE  WHEN  B.HOUSE_CD  =  '082'  THEN  '001'  ELSE  '002'  END  AS  INCOMPRES_GBN  ,  B.NATY_CD  AS  NAT_CD  ,  B.MB_GBN1  FROM  FIDU.TTAC_COPYRTAL  A,  FIDU.TMEM_MB  B,  FIDU.TMEM_ADBK  C,  FIDU.TTAC_DEDCTAMT  D,  FIDU.TTAC_DEDCTAMT  E,  FIDU.TTAC_DEDCTAMT  F,  FIDU.TTAC_BANKFILE  G  WHERE  1  =  1   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  TOT_ORGDISTR_AMT  >  0   \n";
        query +=" AND  B.MB_GBN1  NOT  IN  ('P','F',  'B',  'C'  )   \n";
        query +=" AND  A.MB_CD  NOT  IN   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD='00406'   \n";
        query +=" AND  CODE_CD  LIKE  'IB%'  )   \n";
        query +=" AND  A.MB_CD  =  C.MB_CD(+)   \n";
        query +=" AND  C.ADDR_GBN(+)  =  '1'   \n";
        query +=" AND  A.SUPP_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  A.MB_CD  =  D.MB_CD(+)   \n";
        query +=" AND  A.TRNSF_GBN  =  D.TRNSF_GBN(+)   \n";
        query +=" AND  D.DEDCT_GBNONE(+)  =  '01'   \n";
        query +=" AND  D.DEDCT_GBNTWO(+)  =  '01'   \n";
        query +=" AND  A.SUPP_YRMN  =  E.SUPP_YRMN(+)   \n";
        query +=" AND  A.MB_CD  =  E.MB_CD(+)   \n";
        query +=" AND  A.TRNSF_GBN  =  E.TRNSF_GBN(+)   \n";
        query +=" AND  E.DEDCT_GBNONE(+)  =  '01'   \n";
        query +=" AND  E.DEDCT_GBNTWO(+)  =  '02'   \n";
        query +=" AND  A.SUPP_YRMN  =  F.SUPP_YRMN(+)   \n";
        query +=" AND  A.MB_CD  =  F.MB_CD(+)   \n";
        query +=" AND  A.TRNSF_GBN  =  F.TRNSF_GBN(+)   \n";
        query +=" AND  F.DEDCT_GBNONE(+)  =  '01'   \n";
        query +=" AND  F.DEDCT_GBNTWO(+)  =  '03'   \n";
        query +=" AND  A.SUPP_YRMN  =  G.SUPP_YRMN(+)   \n";
        query +=" AND  A.MB_CD  =  G.MB_CD(+)   \n";
        query +=" AND  A.TRNSF_GBN  =  G.TRNSF_GBN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  :SUPP_YRMN  )  UNION  ALL   \n";
        query +=" SELECT  SS_NUM,  INCOM_GBN,  SUPP_DAY,  RETURN_YRMN,  DUTY_DAYFREQ,  SUPP_TOTAMT,  NEED_EXPS,  INCOM_AMT,  TAXRATE,  INCOMTAX,  CORPTAX,  INHABTAX,  VILL_SPCTAX,  TAX_AMT_TOTAL,  USE_YN,  INS_DAY,  INS_STAFF,  MOD_DAY,  MOD_STAFF,  NM,  ADDR,  FNM,  BIPLC_ADDR,  PHON_NUM,  MOBILE,  BANK_CD,  ACCN_NUM,  RESIDENT_GBN,  HOUSE_CD,  INCOM_GBN_CD,  DETED_ADDR,  POST_NUM,  SUPP_DEPOSITOR,  BIPLC_DETD_ADDR,  BIPLC_POST_NUM,  CHIT_CD,  ACCTN_GBN,  GUBUN_SUCCE,  SUSU,  MEM_NO,  INCOMPRES_GBN,  CASE  WHEN  GUBUN_SUCCE  =  2   \n";
        query +=" AND  RESIDENT_GBN  =  '001'  THEN  INCOM_AMT  -  NEED_EXPS  WHEN  GUBUN_SUCCE  =  2   \n";
        query +=" AND  RESIDENT_GBN  =  '002'  THEN  INCOM_AMT  ELSE  INCOM_AMT  END  AS  INCOME_TAX_AMT,  CASE  WHEN  RESIDENT_GBN  =  '001'   \n";
        query +=" AND  MB_GBN1  <>  'U'  THEN  RESIDENT_GBN  ||  INCOM_GBN  WHEN  RESIDENT_GBN  =  '002'   \n";
        query +=" AND  MB_GBN1  <>  'U'  THEN  RESIDENT_GBN  ||  '001'  WHEN  RESIDENT_GBN  =  '002'   \n";
        query +=" AND  MB_GBN1  =  'U'  THEN  RESIDENT_GBN  ||  '004'  ELSE  INCOM_GBN  ||  '999'  END  AS  INCOME_GBN_D  ,  CASE  WHEN  INCOM_GBN  =  '001'   \n";
        query +=" AND  GUBUN_SUCCE  =  '1'   \n";
        query +=" AND  RESIDENT_GBN  =  '001'  THEN  INCOM_GBN  ||  RESIDENT_GBN  WHEN  INCOM_GBN  =  '002'   \n";
        query +=" AND  GUBUN_SUCCE  =  '2'   \n";
        query +=" AND  RESIDENT_GBN  =  '001'  THEN  INCOM_GBN  ||  RESIDENT_GBN  WHEN  INCOM_GBN  =  '001'   \n";
        query +=" AND  GUBUN_SUCCE  =  '1'   \n";
        query +=" AND  RESIDENT_GBN  =  '002'  THEN  INCOM_GBN  ||  RESIDENT_GBN  WHEN  INCOM_GBN  =  '002'   \n";
        query +=" AND  GUBUN_SUCCE  =  '2'   \n";
        query +=" AND  RESIDENT_GBN  =  '002'  THEN  INCOM_GBN  ||  RESIDENT_GBN  WHEN  INCOM_GBN  =  '004'   \n";
        query +=" AND  RESIDENT_GBN  =  '002'  THEN  INCOM_GBN  ||  RESIDENT_GBN  ELSE  INCOM_GBN  ||  '999'  END  AS  INCOME_GBN_ETC  ,  NAT_CD  FROM   \n";
        query +=" (SELECT  B.INS_NUM  AS  SS_NUM,  CASE  WHEN  B.MB_CD  =  '10012619'  THEN  '004'  WHEN  A.TRNSF_GBN  =  '1'   \n";
        query +=" AND  B.MB_GBN1  <>  'U'  THEN  '001'  WHEN  A.TRNSF_GBN  =  '2'   \n";
        query +=" AND  B.MB_GBN1  <>  'U'  THEN  '002'  ELSE  '004'  END  AS  INCOM_GBN,  A.SUPP_YRMN||'23'  AS  SUPP_DAY,  A.SUPP_YRMN  AS  RETURN_YRMN,  0  AS  DUTY_DAYFREQ,  A.TOT_REALSUPP_AMT  AS  SUPP_TOTAMT,  D.DEDCT_AMT  AS  NEED_EXPS,  (A.TOT_REALSUPP_AMT  +  A.TOT_DEDCT_AMT)  AS  INCOM_AMT,  E.APPL_TAXRATE  AS  TAXRATE,  E.DEDCT_AMT  AS  INCOMTAX,  0  AS  CORPTAX,  F.DEDCT_AMT  AS  INHABTAX,  0  AS  VILL_SPCTAX,  E.DEDCT_AMT  +  F.DEDCT_AMT  AS  TAX_AMT_TOTAL,  'Y'  AS  USE_YN,  TO_CHAR(SYSDATE,  'YYYYMMDD')  AS  INS_DAY,  :INS_STAFF  AS  INS_STAFF,  TO_CHAR(SYSDATE,  'YYYYMMDD')  AS  MOD_DAY,  '  '  AS  MOD_STAFF,  B.HANMB_NM  AS  NM,  C.ADDR  AS  ADDR,  B.INS_NUM  AS  BIOWN_INSNUM,  B.HANMB_NM  AS  FNM,  C.ADDR  AS  BIPLC_ADDR,  B.PHON_NUM  AS  PHON_NUM,  B.CP_NUM  AS  MOBILE,  B.SUPPBANK_CD  AS  BANK_CD,  B.SUPPACCN_NUM  AS  ACCN_NUM,  CASE  WHEN  B.HOUSE_CD  =  '082'  THEN  '001'  ELSE  '002'  END  AS  RESIDENT_GBN,  B.HOUSE_CD  AS  HOUSE_CD,  '001'  AS  INCOM_GBN_CD,  C.ADDR_DETED  AS  DETED_ADDR,  C.POST_NUM  AS  POST_NUM,  B.HANMB_NM  AS  SUPP_DEPOSITOR,  C.ADDR_DETED  AS  BIPLC_DETD_ADDR,  C.POST_NUM  AS  BIPLC_POST_NUM,  '  '  AS  CHIT_CD,  '001'  AS  ACCTN_GBN,  A.TRNSF_GBN  AS  GUBUN_SUCCE,  D.DEDCT_AMT  AS  SUSU,  A.MB_CD  AS  MEM_NO,  CASE  WHEN  B.HOUSE_CD  =  '082'  THEN  '001'  ELSE  '002'  END  AS  INCOMPRES_GBN  ,  B.NATY_CD  AS  NAT_CD  ,  B.MB_GBN1  FROM  FIDU.TTAC_COPYRTAL  A,  FIDU.TMEM_MB  B,  FIDU.TMEM_ADBK  C,  FIDU.TTAC_DEDCTAMT  D,  FIDU.TTAC_DEDCTAMT  E,  FIDU.TTAC_DEDCTAMT  F,  FIDU.TTAC_BANKFILE  G  WHERE  1  =  1   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  TOT_ORGDISTR_AMT  >  0   \n";
        query +=" AND  A.MB_CD  =  '10012619'   \n";
        query +=" AND  A.MB_CD  =  C.MB_CD(+)   \n";
        query +=" AND  C.ADDR_GBN(+)  =  '1'   \n";
        query +=" AND  A.SUPP_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  A.MB_CD  =  D.MB_CD(+)   \n";
        query +=" AND  A.TRNSF_GBN  =  D.TRNSF_GBN(+)   \n";
        query +=" AND  D.DEDCT_GBNONE(+)  =  '01'   \n";
        query +=" AND  D.DEDCT_GBNTWO(+)  =  '01'   \n";
        query +=" AND  A.SUPP_YRMN  =  E.SUPP_YRMN(+)   \n";
        query +=" AND  A.MB_CD  =  E.MB_CD(+)   \n";
        query +=" AND  A.TRNSF_GBN  =  E.TRNSF_GBN(+)   \n";
        query +=" AND  E.DEDCT_GBNONE(+)  =  '01'   \n";
        query +=" AND  E.DEDCT_GBNTWO(+)  =  '02'   \n";
        query +=" AND  A.SUPP_YRMN  =  F.SUPP_YRMN(+)   \n";
        query +=" AND  A.MB_CD  =  F.MB_CD(+)   \n";
        query +=" AND  A.TRNSF_GBN  =  F.TRNSF_GBN(+)   \n";
        query +=" AND  F.DEDCT_GBNONE(+)  =  '01'   \n";
        query +=" AND  F.DEDCT_GBNTWO(+)  =  '03'   \n";
        query +=" AND  A.SUPP_YRMN  =  G.SUPP_YRMN(+)   \n";
        query +=" AND  A.MB_CD  =  G.MB_CD(+)   \n";
        query +=" AND  A.TRNSF_GBN  =  G.TRNSF_GBN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  :SUPP_YRMN  )  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("INS_STAFF", INS_STAFF);               //��� ���
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    // 1
    // ��õ�����̺� ����
    public DOBJ CALLfi_end_close_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("SEL1");           //��õ���������� ������Ų OBJECT�Դϴ�.(CALLfi_end_close_SEL1)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLfi_end_close_INS1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS1") ;
        rvobj.Println("INS1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLfi_end_close_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double SEQ = 0;        //������ȣ
        String   DETED_ADDR = dvobj.getRecord().get("DETED_ADDR");   //�� �ּ�
        String   ACCN_NUM = dvobj.getRecord().get("ACCN_NUM");   //���� ��ȣ
        String   INCOME_TAX_AMT = dvobj.getRecord().get("INCOME_TAX_AMT");
        String   FNM = dvobj.getRecord().get("FNM");   //��ȣ��
        double   TAXRATE = dvobj.getRecord().getDouble("TAXRATE");   //����
        double   INHABTAX = dvobj.getRecord().getDouble("INHABTAX");   //�ֹμ�
        String   CHIT_CD = dvobj.getRecord().get("CHIT_CD");   //��ǥ �ڵ�(��ǥ����(8) + ����(4))
        String   ACCTN_GBN = dvobj.getRecord().get("ACCTN_GBN");   //ȸ�� ����(TENV_CODE_GROUP�� HIGH_CD = '00114')
        double   SUPP_TOTAMT = dvobj.getRecord().getDouble("SUPP_TOTAMT");   //���� �Ѿ�
        String   BIPLC_DETD_ADDR = dvobj.getRecord().get("BIPLC_DETD_ADDR");
        String   SS_NUM = dvobj.getRecord().get("SS_NUM");   //�ֹε�Ϲ�ȣ
        String   GUBUN_SUCCE = dvobj.getRecord().get("GUBUN_SUCCE");   //�°豸��
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        String   POST_NUM = dvobj.getRecord().get("POST_NUM");   //���� ��ȣ
        String   MEM_NO = dvobj.getRecord().get("MEM_NO");   //ȸ����ȣ
        String   NAT_CD = dvobj.getRecord().get("NAT_CD");
        double   CORPTAX = dvobj.getRecord().getDouble("CORPTAX");   //���μ�
        String   BIPLC_POST_NUM = dvobj.getRecord().get("BIPLC_POST_NUM");   //���������ȣ
        String   MOD_DAY = dvobj.getRecord().get("MOD_DAY");   //���� ����
        String   MOBILE = dvobj.getRecord().get("MOBILE");   //�ڵ���
        String   ADDR = dvobj.getRecord().get("ADDR");   //�����ּ�
        String   SUPP_DAY = dvobj.getRecord().get("SUPP_DAY");   //���� ����
        String   RETURN_YRMN = dvobj.getRecord().get("RETURN_YRMN");   //�ͼӳ��
        String   RESIDENT_GBN = dvobj.getRecord().get("RESIDENT_GBN");   //���ֱ���
        String   USE_YN = dvobj.getRecord().get("USE_YN");   //��뱸��
        String   NM = dvobj.getRecord().get("NM");   //����
        String   INCOM_GBN_CD = dvobj.getRecord().get("INCOM_GBN_CD");   //�ҵ汸���ڵ�
        String   INCOM_GBN = dvobj.getRecord().get("INCOM_GBN");   //�ҵ� ����
        double   NEED_EXPS = dvobj.getRecord().getDouble("NEED_EXPS");   //�ʿ���
        double   SUSU = dvobj.getRecord().getDouble("SUSU");   //����������
        String   INCOME_GBN_ETC = dvobj.getRecord().get("INCOME_GBN_ETC");
        double   INCOMTAX = dvobj.getRecord().getDouble("INCOMTAX");   //�ҵ漼
        String   INCOMPRES_GBN = dvobj.getRecord().get("INCOMPRES_GBN");   //�ҵ��ڱ���
        double   INCOM_AMT = dvobj.getRecord().getDouble("INCOM_AMT");   //�ҵ� �ݾ�
        String   SUPP_DEPOSITOR = dvobj.getRecord().get("SUPP_DEPOSITOR");   //���޿�����
        String   INCOME_GBN_D = dvobj.getRecord().get("INCOME_GBN_D");
        String   PHON_NUM = dvobj.getRecord().get("PHON_NUM");   //��ȭ ��ȣ
        String   INS_STAFF = dvobj.getRecord().get("INS_STAFF");   //��� ���
        double   VILL_SPCTAX = dvobj.getRecord().getDouble("VILL_SPCTAX");   //����� Ư����
        String   BANK_CD = dvobj.getRecord().get("BANK_CD");   //���� �ڵ�
        String   MOD_STAFF = dvobj.getRecord().get("MOD_STAFF");   //���� ���
        double   TAX_AMT_TOTAL = dvobj.getRecord().getDouble("TAX_AMT_TOTAL");   //�����հ�
        String   BIPLC_ADDR = dvobj.getRecord().get("BIPLC_ADDR");   //����� �ּ�
        double   DUTY_DAYFREQ = dvobj.getRecord().getDouble("DUTY_DAYFREQ");   //�ٹ��ϼ�
        String   HOUSE_CD = dvobj.getRecord().get("HOUSE_CD");   //������ �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into ACCT.TTAM_INCOME_D (HOUSE_CD, DUTY_DAYFREQ, BIPLC_ADDR, TAX_AMT_TOTAL, MOD_STAFF, BANK_CD, VILL_SPCTAX, INS_STAFF, PHON_NUM, INCOME_GBN_D, SUPP_DEPOSITOR, INCOM_AMT, INCOMPRES_GBN, INCOMTAX, INCOME_GBN_ETC, SUSU, NEED_EXPS, INCOM_GBN, INCOM_GBN_CD, NM, USE_YN, RESIDENT_GBN, RETURN_YRMN, SUPP_DAY, ADDR, MOBILE, MOD_DAY, BIPLC_POST_NUM, CORPTAX, NAT_CD, MEM_NO, POST_NUM, INS_DAY, GUBUN_SUCCE, SEQ, SS_NUM, BIPLC_DETD_ADDR, SUPP_TOTAMT, ACCTN_GBN, CHIT_CD, INHABTAX, TAXRATE, FNM, INCOME_TAX_AMT, ACCN_NUM, DETED_ADDR)  \n";
        query +=" values(:HOUSE_CD , :DUTY_DAYFREQ , :BIPLC_ADDR , :TAX_AMT_TOTAL , :MOD_STAFF , :BANK_CD , :VILL_SPCTAX , :INS_STAFF , :PHON_NUM , :INCOME_GBN_D , :SUPP_DEPOSITOR , :INCOM_AMT , :INCOMPRES_GBN , :INCOMTAX , :INCOME_GBN_ETC , :SUSU , :NEED_EXPS , :INCOM_GBN , :INCOM_GBN_CD , :NM , :USE_YN , :RESIDENT_GBN , :RETURN_YRMN , :SUPP_DAY , :ADDR , :MOBILE , :MOD_DAY , :BIPLC_POST_NUM , :CORPTAX , :NAT_CD , :MEM_NO , :POST_NUM , :INS_DAY , :GUBUN_SUCCE , (SELECT NVL(max(seq),0)+1 from acct.ttam_income_d), :SS_NUM , :BIPLC_DETD_ADDR , :SUPP_TOTAMT , :ACCTN_GBN , :CHIT_CD , :INHABTAX , :TAXRATE , :FNM , :INCOME_TAX_AMT , :ACCN_NUM , :DETED_ADDR )";
        sobj.setSql(query);
        sobj.setString("DETED_ADDR", DETED_ADDR);               //�� �ּ�
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setString("INCOME_TAX_AMT", INCOME_TAX_AMT);
        sobj.setString("FNM", FNM);               //��ȣ��
        sobj.setDouble("TAXRATE", TAXRATE);               //����
        sobj.setDouble("INHABTAX", INHABTAX);               //�ֹμ�
        sobj.setString("CHIT_CD", CHIT_CD);               //��ǥ �ڵ�(��ǥ����(8) + ����(4))
        sobj.setString("ACCTN_GBN", ACCTN_GBN);               //ȸ�� ����(TENV_CODE_GROUP�� HIGH_CD = '00114')
        sobj.setDouble("SUPP_TOTAMT", SUPP_TOTAMT);               //���� �Ѿ�
        sobj.setString("BIPLC_DETD_ADDR", BIPLC_DETD_ADDR);
        sobj.setString("SS_NUM", SS_NUM);               //�ֹε�Ϲ�ȣ
        sobj.setString("GUBUN_SUCCE", GUBUN_SUCCE);               //�°豸��
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("POST_NUM", POST_NUM);               //���� ��ȣ
        sobj.setString("MEM_NO", MEM_NO);               //ȸ����ȣ
        sobj.setString("NAT_CD", NAT_CD);
        sobj.setDouble("CORPTAX", CORPTAX);               //���μ�
        sobj.setString("BIPLC_POST_NUM", BIPLC_POST_NUM);               //���������ȣ
        sobj.setString("MOD_DAY", MOD_DAY);               //���� ����
        sobj.setString("MOBILE", MOBILE);               //�ڵ���
        sobj.setString("ADDR", ADDR);               //�����ּ�
        sobj.setString("SUPP_DAY", SUPP_DAY);               //���� ����
        sobj.setString("RETURN_YRMN", RETURN_YRMN);               //�ͼӳ��
        sobj.setString("RESIDENT_GBN", RESIDENT_GBN);               //���ֱ���
        sobj.setString("USE_YN", USE_YN);               //��뱸��
        sobj.setString("NM", NM);               //����
        sobj.setString("INCOM_GBN_CD", INCOM_GBN_CD);               //�ҵ汸���ڵ�
        sobj.setString("INCOM_GBN", INCOM_GBN);               //�ҵ� ����
        sobj.setDouble("NEED_EXPS", NEED_EXPS);               //�ʿ���
        sobj.setDouble("SUSU", SUSU);               //����������
        sobj.setString("INCOME_GBN_ETC", INCOME_GBN_ETC);
        sobj.setDouble("INCOMTAX", INCOMTAX);               //�ҵ漼
        sobj.setString("INCOMPRES_GBN", INCOMPRES_GBN);               //�ҵ��ڱ���
        sobj.setDouble("INCOM_AMT", INCOM_AMT);               //�ҵ� �ݾ�
        sobj.setString("SUPP_DEPOSITOR", SUPP_DEPOSITOR);               //���޿�����
        sobj.setString("INCOME_GBN_D", INCOME_GBN_D);
        sobj.setString("PHON_NUM", PHON_NUM);               //��ȭ ��ȣ
        sobj.setString("INS_STAFF", INS_STAFF);               //��� ���
        sobj.setDouble("VILL_SPCTAX", VILL_SPCTAX);               //����� Ư����
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("MOD_STAFF", MOD_STAFF);               //���� ���
        sobj.setDouble("TAX_AMT_TOTAL", TAX_AMT_TOTAL);               //�����հ�
        sobj.setString("BIPLC_ADDR", BIPLC_ADDR);               //����� �ּ�
        sobj.setDouble("DUTY_DAYFREQ", DUTY_DAYFREQ);               //�ٹ��ϼ�
        sobj.setString("HOUSE_CD", HOUSE_CD);               //������ �ڵ�
        return sobj;
    }
    // ��������������
    public DOBJ CALLfi_end_close_UNI4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("UNI4");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLfi_end_close_UNI4UPD(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLfi_end_close_UNI4INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI4") ;
        rvobj.Println("UNI4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLfi_end_close_UNI4UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_DATE ="";        //���� �Ͻ�
        String   END_YN ="1";   //���� ����
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_ACCTNEND  \n";
        query +=" set END_YN=:END_YN , END_DATE=SYSDATE  \n";
        query +=" where SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("END_YN", END_YN);               //���� ����
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    private SQLObject SQLfi_end_close_UNI4INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_DATE ="";        //���� �Ͻ�
        String   END_YN ="1";   //���� ����
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_ACCTNEND (END_YN, END_DATE, SUPP_YRMN)  \n";
        query +=" values(:END_YN , SYSDATE, :SUPP_YRMN )";
        sobj.setSql(query);
        sobj.setString("END_YN", END_YN);               //���� ����
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    // 0�αݾ� ����
    // �����̿�, ����߻�, �������, ����ܾ�, ��������� ���ݾ��� 0�̸� ���� ó����
    public DOBJ CALLfi_end_close_XIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD13");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLfi_end_close_XIUD13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLfi_end_close_XIUD13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FIDU.TTAC_PRODSUSPREMD  \n";
        query +=" WHERE BFRMON_REMD = 0  \n";
        query +=" AND PREMON_OCC_AMT = 0  \n";
        query +=" AND PREMON_RELS_AMT = 0  \n";
        query +=" AND PREMON_REMD = 0  \n";
        query +=" AND MOD_AMT = 0  \n";
        query +=" AND SUPP_YRMN = :SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    //##**$$fi_end_close
    //##**$$homp_end_close
    /*
    */
    public DOBJ CTLhomp_end_close(DOBJ dobj)
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
            dobj  = CALLhomp_end_close_UNI1(Conn, dobj);           //  ��������������
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
    public DOBJ CTLhomp_end_close( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLhomp_end_close_UNI1(Conn, dobj);           //  ��������������
        return dobj;
    }
    // ��������������
    public DOBJ CALLhomp_end_close_UNI1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("UNI1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLhomp_end_close_UNI1UPD(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLhomp_end_close_UNI1INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI1") ;
        rvobj.Println("UNI1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLhomp_end_close_UNI1UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String HOMP_END_DATE ="";        //���� �Ͻ�
        String   HOMP_END_YN ="1";   //���� ����
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_ACCTNEND  \n";
        query +=" set HOMP_END_DATE=SYSDATE , HOMP_END_YN=:HOMP_END_YN  \n";
        query +=" where SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("HOMP_END_YN", HOMP_END_YN);               //���� ����
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    private SQLObject SQLhomp_end_close_UNI1INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String HOMP_END_DATE ="";        //���� �Ͻ�
        String   HOMP_END_YN ="1";   //���� ����
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_ACCTNEND (HOMP_END_DATE, HOMP_END_YN, SUPP_YRMN)  \n";
        query +=" values(SYSDATE, :HOMP_END_YN , :SUPP_YRMN )";
        sobj.setSql(query);
        sobj.setString("HOMP_END_YN", HOMP_END_YN);               //���� ����
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    //##**$$homp_end_close
    //##**$$homp_end_cancle
    /*
    */
    public DOBJ CTLhomp_end_cancle(DOBJ dobj)
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
            dobj  = CALLhomp_end_cancle_XIUD1(Conn, dobj);           //  ��Źȸ�踶������
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
    public DOBJ CTLhomp_end_cancle( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLhomp_end_cancle_XIUD1(Conn, dobj);           //  ��Źȸ�踶������
        return dobj;
    }
    // ��Źȸ�踶������
    public DOBJ CALLhomp_end_cancle_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLhomp_end_cancle_XIUD1(dobj, dvobj);
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
    private SQLObject SQLhomp_end_cancle_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE FIDU.TTAC_ACCTNEND  \n";
        query +=" SET HOMP_END_YN = '0'  \n";
        query +=" WHERE SUPP_YRMN = :SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    //##**$$homp_end_cancle
    //##**$$end
}
