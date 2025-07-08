
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac09_sodek
{
    public tac09_sodek()
    {
    }
    //##**$$sodek_update
    /*
    */
    public DOBJ CTLsodek_update(DOBJ dobj)
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
            dobj  = CALLsodek_update_SEL1(Conn, dobj);           //  원천세생성
            dobj  = CALLsodek_update_INS1(Conn, dobj);           //  1
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
    public DOBJ CTLsodek_update( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsodek_update_SEL1(Conn, dobj);           //  원천세생성
        dobj  = CALLsodek_update_INS1(Conn, dobj);           //  1
        return dobj;
    }
    // 원천세생성
    public DOBJ CALLsodek_update_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsodek_update_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsodek_update_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   INS_STAFF = dobj.getRetObject("G").getRecord().get("USERID");   //등록 사원
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        String   SUPP_YRMN1 = dobj.getRetObject("S").getRecord().get("SUPP_YRMN1");   //지급 년월
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
        query +=" AND  A.MB_CD  =  :MB_CD   \n";
        query +=" AND  B.MB_GBN1  NOT  IN  ('P','F')   \n";
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
        query +=" AND  A.SUPP_YRMN  >=  :SUPP_YRMN   \n";
        query +=" AND  A.SUPP_YRMN  <=  :SUPP_YRMN1  ) ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("INS_STAFF", INS_STAFF);               //등록 사원
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("SUPP_YRMN1", SUPP_YRMN1);               //지급 년월
        return sobj;
    }
    // 1
    // 원천세테이블에 저장
    public DOBJ CALLsodek_update_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("SEL1");           //원천세생성에서 생성시킨 OBJECT입니다.(CALLsodek_update_SEL1)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsodek_update_INS1(dobj, dvobj);
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
    private SQLObject SQLsodek_update_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double SEQ = 0;        //관리번호
        String   DETED_ADDR = dvobj.getRecord().get("DETED_ADDR");   //상세 주소
        String   ACCN_NUM = dvobj.getRecord().get("ACCN_NUM");   //계좌 번호
        String   INCOME_TAX_AMT = dvobj.getRecord().get("INCOME_TAX_AMT");
        String   FNM = dvobj.getRecord().get("FNM");   //상호명
        double   TAXRATE = dvobj.getRecord().getDouble("TAXRATE");   //세율
        double   INHABTAX = dvobj.getRecord().getDouble("INHABTAX");   //주민세
        String   CHIT_CD = dvobj.getRecord().get("CHIT_CD");   //전표 코드(전표일자(8) + 순번(4))
        String   ACCTN_GBN = dvobj.getRecord().get("ACCTN_GBN");   //회계 구분(TENV_CODE_GROUP의 HIGH_CD = '00114')
        double   SUPP_TOTAMT = dvobj.getRecord().getDouble("SUPP_TOTAMT");   //지급 총액
        String   BIPLC_DETD_ADDR = dvobj.getRecord().get("BIPLC_DETD_ADDR");
        String   SS_NUM = dvobj.getRecord().get("SS_NUM");   //주민등록번호
        String   GUBUN_SUCCE = dvobj.getRecord().get("GUBUN_SUCCE");   //승계구분
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //등록 일자
        String   POST_NUM = dvobj.getRecord().get("POST_NUM");   //우편 번호
        String   MEM_NO = dvobj.getRecord().get("MEM_NO");   //회원번호
        String   NAT_CD = dvobj.getRecord().get("NAT_CD");
        double   CORPTAX = dvobj.getRecord().getDouble("CORPTAX");   //법인세
        String   BIPLC_POST_NUM = dvobj.getRecord().get("BIPLC_POST_NUM");   //사업장우편번호
        String   MOD_DAY = dvobj.getRecord().get("MOD_DAY");   //수정 일자
        String   MOBILE = dvobj.getRecord().get("MOBILE");   //핸드폰
        String   ADDR = dvobj.getRecord().get("ADDR");   //거주주소
        String   SUPP_DAY = dvobj.getRecord().get("SUPP_DAY");   //지급 일자
        String   RETURN_YRMN = dvobj.getRecord().get("RETURN_YRMN");   //귀속년월
        String   RESIDENT_GBN = dvobj.getRecord().get("RESIDENT_GBN");   //거주구분
        String   USE_YN = dvobj.getRecord().get("USE_YN");   //사용구분
        String   NM = dvobj.getRecord().get("NM");   //성명
        String   INCOM_GBN_CD = dvobj.getRecord().get("INCOM_GBN_CD");   //소득구분코드
        String   INCOM_GBN = dvobj.getRecord().get("INCOM_GBN");   //소득 구분
        double   NEED_EXPS = dvobj.getRecord().getDouble("NEED_EXPS");   //필요경비
        double   SUSU = dvobj.getRecord().getDouble("SUSU");   //관리수수료
        String   INCOME_GBN_ETC = dvobj.getRecord().get("INCOME_GBN_ETC");
        double   INCOMTAX = dvobj.getRecord().getDouble("INCOMTAX");   //소득세
        String   INCOMPRES_GBN = dvobj.getRecord().get("INCOMPRES_GBN");   //소득자구분
        double   INCOM_AMT = dvobj.getRecord().getDouble("INCOM_AMT");   //소득 금액
        String   SUPP_DEPOSITOR = dvobj.getRecord().get("SUPP_DEPOSITOR");   //지급예금주
        String   INCOME_GBN_D = dvobj.getRecord().get("INCOME_GBN_D");
        String   PHON_NUM = dvobj.getRecord().get("PHON_NUM");   //전화 번호
        String   INS_STAFF = dvobj.getRecord().get("INS_STAFF");   //등록 사원
        double   VILL_SPCTAX = dvobj.getRecord().getDouble("VILL_SPCTAX");   //농어촌 특별세
        String   BANK_CD = dvobj.getRecord().get("BANK_CD");   //은행 코드
        String   MOD_STAFF = dvobj.getRecord().get("MOD_STAFF");   //수정 사원
        double   TAX_AMT_TOTAL = dvobj.getRecord().getDouble("TAX_AMT_TOTAL");   //세액합계
        String   BIPLC_ADDR = dvobj.getRecord().get("BIPLC_ADDR");   //사업장 주소
        double   DUTY_DAYFREQ = dvobj.getRecord().getDouble("DUTY_DAYFREQ");   //근무일수
        String   HOUSE_CD = dvobj.getRecord().get("HOUSE_CD");   //거주지 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into ACCT.TTAM_INCOME_D (HOUSE_CD, DUTY_DAYFREQ, BIPLC_ADDR, TAX_AMT_TOTAL, MOD_STAFF, BANK_CD, VILL_SPCTAX, INS_STAFF, PHON_NUM, INCOME_GBN_D, SUPP_DEPOSITOR, INCOM_AMT, INCOMPRES_GBN, INCOMTAX, INCOME_GBN_ETC, SUSU, NEED_EXPS, INCOM_GBN, INCOM_GBN_CD, NM, USE_YN, RESIDENT_GBN, RETURN_YRMN, SUPP_DAY, ADDR, MOBILE, MOD_DAY, BIPLC_POST_NUM, CORPTAX, NAT_CD, MEM_NO, POST_NUM, INS_DAY, GUBUN_SUCCE, SEQ, SS_NUM, BIPLC_DETD_ADDR, SUPP_TOTAMT, ACCTN_GBN, CHIT_CD, INHABTAX, TAXRATE, FNM, INCOME_TAX_AMT, ACCN_NUM, DETED_ADDR)  \n";
        query +=" values(:HOUSE_CD , :DUTY_DAYFREQ , :BIPLC_ADDR , :TAX_AMT_TOTAL , :MOD_STAFF , :BANK_CD , :VILL_SPCTAX , :INS_STAFF , :PHON_NUM , :INCOME_GBN_D , :SUPP_DEPOSITOR , :INCOM_AMT , :INCOMPRES_GBN , :INCOMTAX , :INCOME_GBN_ETC , :SUSU , :NEED_EXPS , :INCOM_GBN , :INCOM_GBN_CD , :NM , :USE_YN , :RESIDENT_GBN , :RETURN_YRMN , :SUPP_DAY , :ADDR , :MOBILE , :MOD_DAY , :BIPLC_POST_NUM , :CORPTAX , :NAT_CD , :MEM_NO , :POST_NUM , :INS_DAY , :GUBUN_SUCCE , (SELECT NVL(max(seq),0)+1 from acct.ttam_income_d), :SS_NUM , :BIPLC_DETD_ADDR , :SUPP_TOTAMT , :ACCTN_GBN , :CHIT_CD , :INHABTAX , :TAXRATE , :FNM , :INCOME_TAX_AMT , :ACCN_NUM , :DETED_ADDR )";
        sobj.setSql(query);
        sobj.setString("DETED_ADDR", DETED_ADDR);               //상세 주소
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        sobj.setString("INCOME_TAX_AMT", INCOME_TAX_AMT);
        sobj.setString("FNM", FNM);               //상호명
        sobj.setDouble("TAXRATE", TAXRATE);               //세율
        sobj.setDouble("INHABTAX", INHABTAX);               //주민세
        sobj.setString("CHIT_CD", CHIT_CD);               //전표 코드(전표일자(8) + 순번(4))
        sobj.setString("ACCTN_GBN", ACCTN_GBN);               //회계 구분(TENV_CODE_GROUP의 HIGH_CD = '00114')
        sobj.setDouble("SUPP_TOTAMT", SUPP_TOTAMT);               //지급 총액
        sobj.setString("BIPLC_DETD_ADDR", BIPLC_DETD_ADDR);
        sobj.setString("SS_NUM", SS_NUM);               //주민등록번호
        sobj.setString("GUBUN_SUCCE", GUBUN_SUCCE);               //승계구분
        sobj.setString("INS_DAY", INS_DAY);               //등록 일자
        sobj.setString("POST_NUM", POST_NUM);               //우편 번호
        sobj.setString("MEM_NO", MEM_NO);               //회원번호
        sobj.setString("NAT_CD", NAT_CD);
        sobj.setDouble("CORPTAX", CORPTAX);               //법인세
        sobj.setString("BIPLC_POST_NUM", BIPLC_POST_NUM);               //사업장우편번호
        sobj.setString("MOD_DAY", MOD_DAY);               //수정 일자
        sobj.setString("MOBILE", MOBILE);               //핸드폰
        sobj.setString("ADDR", ADDR);               //거주주소
        sobj.setString("SUPP_DAY", SUPP_DAY);               //지급 일자
        sobj.setString("RETURN_YRMN", RETURN_YRMN);               //귀속년월
        sobj.setString("RESIDENT_GBN", RESIDENT_GBN);               //거주구분
        sobj.setString("USE_YN", USE_YN);               //사용구분
        sobj.setString("NM", NM);               //성명
        sobj.setString("INCOM_GBN_CD", INCOM_GBN_CD);               //소득구분코드
        sobj.setString("INCOM_GBN", INCOM_GBN);               //소득 구분
        sobj.setDouble("NEED_EXPS", NEED_EXPS);               //필요경비
        sobj.setDouble("SUSU", SUSU);               //관리수수료
        sobj.setString("INCOME_GBN_ETC", INCOME_GBN_ETC);
        sobj.setDouble("INCOMTAX", INCOMTAX);               //소득세
        sobj.setString("INCOMPRES_GBN", INCOMPRES_GBN);               //소득자구분
        sobj.setDouble("INCOM_AMT", INCOM_AMT);               //소득 금액
        sobj.setString("SUPP_DEPOSITOR", SUPP_DEPOSITOR);               //지급예금주
        sobj.setString("INCOME_GBN_D", INCOME_GBN_D);
        sobj.setString("PHON_NUM", PHON_NUM);               //전화 번호
        sobj.setString("INS_STAFF", INS_STAFF);               //등록 사원
        sobj.setDouble("VILL_SPCTAX", VILL_SPCTAX);               //농어촌 특별세
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("MOD_STAFF", MOD_STAFF);               //수정 사원
        sobj.setDouble("TAX_AMT_TOTAL", TAX_AMT_TOTAL);               //세액합계
        sobj.setString("BIPLC_ADDR", BIPLC_ADDR);               //사업장 주소
        sobj.setDouble("DUTY_DAYFREQ", DUTY_DAYFREQ);               //근무일수
        sobj.setString("HOUSE_CD", HOUSE_CD);               //거주지 코드
        return sobj;
    }
    //##**$$sodek_update
    //##**$$end
}
