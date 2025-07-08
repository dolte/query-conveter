
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac02_s01_ele
{
    public tac02_s01_ele()
    {
    }
    //##**$$elec_bill_insert
    /*
    */
    public DOBJ CTLelec_bill_insert(DOBJ dobj)
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
            dobj  = CALLelec_bill_insert_SEL8(Conn, dobj);           //  BILL_MEMBER 저장할지 등록할지
            if( dobj.getRetObject("SEL8").getRecord().get("MEM_CNT").equals("0"))
            {
                dobj  = CALLelec_bill_insert_XIUD14(Conn, dobj);           //  공급자 회원가입처리
                dobj  = CALLelec_bill_insert_XIUD2(Conn, dobj);           //  전자세금계산서 헤드처리
                dobj  = CALLelec_bill_insert_XIUD3(Conn, dobj);           //  전자계산서품목처리
                dobj  = CALLelec_bill_insert_XIUD5(Conn, dobj);           //  전송처리 'Y' 넣어줌
            }
            else
            {
                dobj  = CALLelec_bill_insert_XIUD19(Conn, dobj);           //  BILL_MEMBER  삭제
                dobj  = CALLelec_bill_insert_XIUD2(Conn, dobj);           //  전자세금계산서 헤드처리
                dobj  = CALLelec_bill_insert_XIUD3(Conn, dobj);           //  전자계산서품목처리
                dobj  = CALLelec_bill_insert_XIUD5(Conn, dobj);           //  전송처리 'Y' 넣어줌
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
    public DOBJ CTLelec_bill_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLelec_bill_insert_SEL8(Conn, dobj);           //  BILL_MEMBER 저장할지 등록할지
        if( dobj.getRetObject("SEL8").getRecord().get("MEM_CNT").equals("0"))
        {
            dobj  = CALLelec_bill_insert_XIUD14(Conn, dobj);           //  공급자 회원가입처리
            dobj  = CALLelec_bill_insert_XIUD2(Conn, dobj);           //  전자세금계산서 헤드처리
            dobj  = CALLelec_bill_insert_XIUD3(Conn, dobj);           //  전자계산서품목처리
            dobj  = CALLelec_bill_insert_XIUD5(Conn, dobj);           //  전송처리 'Y' 넣어줌
        }
        else
        {
            dobj  = CALLelec_bill_insert_XIUD19(Conn, dobj);           //  BILL_MEMBER  삭제
            dobj  = CALLelec_bill_insert_XIUD2(Conn, dobj);           //  전자세금계산서 헤드처리
            dobj  = CALLelec_bill_insert_XIUD3(Conn, dobj);           //  전자계산서품목처리
            dobj  = CALLelec_bill_insert_XIUD5(Conn, dobj);           //  전송처리 'Y' 넣어줌
        }
        return dobj;
    }
    // BILL_MEMBER 저장할지 등록할지
    public DOBJ CALLelec_bill_insert_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLelec_bill_insert_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLelec_bill_insert_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPPPRES_CD = dobj.getRetObject("S").getRecord().get("SUPPPRES_CD");   //공급자_코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  MEM_CNT  FROM  FIDU.BILL_MEMBER  WHERE  ID  =:SUPPPRES_CD ";
        sobj.setSql(query);
        sobj.setString("SUPPPRES_CD", SUPPPRES_CD);               //공급자_코드
        return sobj;
    }
    // 공급자 회원가입처리
    // BILL_MEMBER에 넣어줌
    public DOBJ CALLelec_bill_insert_XIUD14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD14");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLelec_bill_insert_XIUD14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLelec_bill_insert_XIUD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPPPRES_CD = dobj.getRetObject("S").getRecord().get("SUPPPRES_CD");   //공급자_코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.BILL_MEMBER(ENTCODE, ID, COMPANY, VENDERNO, CEONAME, UPTAE, UPJONG, MGMT_YN, EMAIL_YN, SMS_YN, CREATE_DT, RESULT_CD,ZIPCODE,ADDRESS,ADDRESS2,EMAIL ) ( SELECT 'A460', A.MB_CD, SUBSTR(HANMB_NM,1,26), INS_NUM ,REPPRES_NM, SUBSTR(NVL(BSCDTN_CTENT,' '),1,15),SUBSTR(NVL(BSTYP_CTENT,' '),1,15),'Y', 'Y', 'Y' , NVL(TO_CHAR(A.INS_DATE,'YYYYMMDD'),TO_CHAR(SYSDATE,'YYYYMMDD')), 'N', B.POST_NUM, B.ADDR, NVL(B.ADDR_DETED,' '), A.EMAIL_ADDR FROM FIDU.TMEM_MB A, FIDU.TMEM_ADBK B WHERE A.MB_CD =:SUPPPRES_CD AND A.MB_CD = B.MB_CD AND B.ADDR_GBN ='1' UNION ALL SELECT 'A460', A.BSCON_CD, SUBSTR(BSCONHAN_NM,1,26), INS_NUM ,REPPRES_NM, SUBSTR(NVL(BSCDTN_CTENT,' '),1,15),SUBSTR(NVL(BSTYP_CTENT,' '),1,15),'Y', 'Y', 'Y' , NVL(TO_CHAR(A.INS_DATE,'YYYYMMDD'),TO_CHAR(SYSDATE,'YYYYMMDD')), 'N', A.POST_NUM, A.ADDR, NVL(A.ADDR_DETED,' '), A.EMAIL_ADDR FROM FIDU.TLEV_BSCON A WHERE BSCON_CD = :SUPPPRES_CD)";
        sobj.setSql(query);
        sobj.setString("SUPPPRES_CD", SUPPPRES_CD);               //공급자_코드
        return sobj;
    }
    // 전자세금계산서 헤드처리
    // 전자세금계산서 품목처리부분  2018.02.27 이다섭 수정 삭제되지 않은 계산서만 처리하도록 DEL_YN IS NULL을 추가
    public DOBJ CALLelec_bill_insert_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD2");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLelec_bill_insert_XIUD2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        rvobj.Println("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLelec_bill_insert_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILLTYPE = dobj.getRetObject("S").getRecord().get("BILLTYPE");   //계산서종류(샌드빌)
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.BILL_TRANS SELECT A.BILL_NUM , A.SUPPPRES_INS_NUM , A.BSCON_INS_NUM , TO_CHAR (TO_DATE (ISS_DAY), 'YYYY-MM-DD') ISS_DAY , SUPPTEMP_AMT , ATAX_AMT , DECODE (A.BILL_KND, '2', '0', '6', '0', '2') , 0 , 0 , 0 , 0 , BILL_GBN , SUBSTR (A.REMAK, 1, 50) , :BILLTYPE , SUBSTR (SUPPPRES_FNM_NM, 1, 26) , SUBSTR (SUPPPRES_REPPRES_NM, 1, 10) , SUBSTR (SUPPPRES_BSCDTN, 1, 13) , SUBSTR (SUPPPRES_BSTYP, 1, 13) , SUBSTR (SUPPPRES_ADDR, 1, 60) , ' ' , '담당' , '담당팀' , NVL (C.PHON_NUM, '26600486') , NVL (E.EMAIL_ADDR, 'acc@komca.or.kr') , '' , SUBSTR (A.BSCON_FNM_NM, 1, 22) , SUBSTR (NVL (A.BSCON_REPPRES_NM, '-'), 1, 10) , SUBSTR (NVL (A.BSCON_BSCDTN, '-'), 1, 13) , SUBSTR (NVL (A.BSCON_BSTYP, '-'), 1, 13) , SUBSTR (A.BSCON_ADDR, 1, 60) , NVL (ADDR_DETED, ' ') , '담당자' , '부서명' , NVL (B.PHON_NUM, ' ') , (CASE WHEN (A.BILL_KND = '1' OR A.BILL_KND = '2') AND NVL (B.EMAIL_ADDR, '') = '' THEN 'acc@komca.or.kr' WHEN (SUBSTR(F.MDM_CD,0,2) = 'DA' OR SUBSTR(F.MDM_CD,0,2) = 'DK') THEN F.ACC_EMAIL_ADDR WHEN (A.BILL_KND = '1' OR A.BILL_KND = '2') AND LENGTH (B.EMAIL_ADDR) > 1 THEN B.EMAIL_ADDR WHEN A.BILL_KND = '3' AND LENGTH (C.EMAIL_ADDR) > 2 THEN C.EMAIL_ADDR WHEN A.BILL_KND = '4' AND LENGTH (D.EMAIL_ADDR) > 2 THEN D.EMAIL_ADDR WHEN A.BILL_KND = '5' AND LENGTH (D.EMAIL_ADDR) > 2 THEN D.EMAIL_ADDR WHEN A.BILL_KND = '6' AND LENGTH (D.EMAIL_ADDR) > 2 THEN D.EMAIL_ADDR ELSE 'acc@komca.or.kr' END) AS EMAIL_ADDR , '' , 'N' , '' , '' , DECODE (A.BILL_KND, '2', 'N', '6', 'N', 'A') , '' , TO_CHAR (SYSDATE, 'YYYYMMDD') , '' , '' , '' , 'N' , 'N' , '' , '' , '' , '' , 'N' , '' , '' , '' , '' , 'A' , '' , '' , '' , '' , '' , 'I' , 'N' , 'Y' , '' , '' , '' , '' , '' , '' , '' , '' , SYSDATE FROM FIDU.TTAC_BILL A , FIDU.TLEV_BSCON B , FIDU.TMEM_MB C , (SELECT BB.BILL_NUM , EMAIL_ADDR , CC.UPSO_CD , CC.UPSO_NM FROM GIBU.TBRA_BILL_ISS_MST AA, GIBU.TBRA_BILL_ISS_DTL BB, GIBU.TBRA_UPSO CC WHERE AA.UPSO_CD = CC.UPSO_CD AND AA.DEMD_NUM = BB.DEMD_NUM) D , FIDU.TMEM_MB E , ( SELECT DISTINCT X.MDM_CD, X.DEMD_NUM, Y.ACC_EMAIL_ADDR, Z.BILL_NUM FROM FIDU.TLEV_CLRREC X, FIDU.TLEV_APPTNPRESINFO Y, FIDU.TTAC_BILL Z WHERE X.APPRV_NUM = Y.APPRV_NUM AND X.DEMD_NUM = Z.DEMD_NUM AND SUBSTR(X.MDM_CD,0,2) IN('DA','DK') AND Z.BILL_NUM=:BILL_NUM AND Z.BILL_NUM NOT IN (SELECT BILL_NUM FROM GIBU.TBRA_BILL_ISS_DTL WHERE BILL_NUM=:BILL_NUM) ) F WHERE A.BSCON_CD = B.BSCON_CD(+) AND A.BSCON_CD = C.MB_CD(+) AND A.SUPPPRES_CD = E.MB_CD(+) AND A.BILL_NUM = :BILL_NUM AND A.BILL_NUM = D.BILL_NUM(+) AND A.BILL_NUM = F.BILL_NUM(+) AND A.DEL_YN IS NULL";
        sobj.setSql(query);
        sobj.setString("BILLTYPE", BILLTYPE);               //계산서종류(샌드빌)
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        return sobj;
    }
    // 전자계산서품목처리
    // 품목을 넣어줌  2018.02.27 이다섭 수정 삭제되지 않은 계산서만 처리하도록 DEL_YN IS NULL을 추가
    public DOBJ CALLelec_bill_insert_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD3");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLelec_bill_insert_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        rvobj.Println("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLelec_bill_insert_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.BILL_TRANS_ITEM SELECT BILL_NUM , ROWNUM AS CLR_NUM , NVL(ATAX_AMT, NVL((SELECT ATAX_AMT FROM GIBU.TBRA_BSCON_DEMD_UPLOAD WHERE UPSO_CD = (SELECT UPSO_CD FROM GIBU.TBRA_BILL_ISS_MST WHERE DEMD_NUM = (SELECT DEMD_NUM FROM GIBU.TBRA_BILL_ISS_DTL WHERE BILL_NUM = :BILL_NUM)) AND DEMD_YRMN = SUBSTR (:BILL_NUM, 1, 6) AND BSCON_CD = (SELECT SUPPBSCON_CD FROM GIBU.TBRA_BILL_ISS_DTL WHERE BILL_NUM = :BILL_NUM) AND (ATAX_AMT > 0 AND (SELECT COUNT(1) FROM GIBU.TBRA_BILL_ISS_DTL WHERE BILL_NUM = :BILL_NUM AND BILL_KND = '6') > 0)), 0)) AS ATAX_AMT , SUPPTEMP_AMT , 0 , DECODE(SUBSTR(:BILL_NUM, 7, 1), 'S', 0, QTY_PNUM) AS QTY_PNUM , DECODE(SUBSTR(:BILL_NUM, 7, 1), 'S', '', FIDU.GET_MDM_NM (MDM_CD)) AS MDM_CD , TITLE_NM , TO_CHAR (TO_DATE (ISS_DAY), 'YYYY-MM-DD') AS ISS_DAY , '' FROM FIDU.TTAC_USEAPPRVBRE WHERE BILL_NUM = :BILL_NUM AND DEL_YN IS NULL";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        return sobj;
    }
    // 전송처리 'Y' 넣어줌
    public DOBJ CALLelec_bill_insert_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLelec_bill_insert_XIUD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLelec_bill_insert_XIUD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE FIDU.TTAC_BILL  \n";
        query +=" SET ELEC_SEND_YN ='1'  \n";
        query +=" WHERE BILL_NUM = :BILL_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        return sobj;
    }
    // BILL_MEMBER  삭제
    // 공급자는 반드시 들어간다고 함 그래서 지우고 들어가도록 처리
    public DOBJ CALLelec_bill_insert_XIUD19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD19");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLelec_bill_insert_XIUD19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLelec_bill_insert_XIUD19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPPPRES_CD = dobj.getRetObject("S").getRecord().get("SUPPPRES_CD");   //공급자_코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE FIDU.BILL_MEMBER  \n";
        query +=" SET CEONAME = (SELECT REPPRES_NM FROM FIDU.TMEM_MB  \n";
        query +=" WHERE MB_CD = :SUPPPRES_CD UNION ALL SELECT REPPRES_NM FROM FIDU.TLEV_BSCON  \n";
        query +=" WHERE BSCON_CD = :SUPPPRES_CD ), COMPANY = (SELECT HANMB_NM FROM FIDU.TMEM_MB  \n";
        query +=" WHERE MB_CD = :SUPPPRES_CD UNION ALL SELECT BSCONHAN_NM FROM FIDU.TLEV_BSCON  \n";
        query +=" WHERE BSCON_CD = :SUPPPRES_CD )  \n";
        query +=" WHERE ID =:SUPPPRES_CD";
        sobj.setSql(query);
        sobj.setString("SUPPPRES_CD", SUPPPRES_CD);               //공급자_코드
        return sobj;
    }
    //##**$$elec_bill_insert
    //##**$$end
}
