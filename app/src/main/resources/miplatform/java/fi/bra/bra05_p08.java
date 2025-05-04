
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_p08
{
    public bra05_p08()
    {
    }
    //##**$$accu_unsol_list
    /* * 프로그램명 : bra05_p08
    * 작성자 : 서정재
    * 작성일 : 2009/09/28
    * 설명 :
    고소등록된 건수중 미해결된 정보를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLaccu_unsol_list(DOBJ dobj)
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
            dobj  = CALLaccu_unsol_list_SEL1(Conn, dobj);           //  고소미해결내역서
            dobj  = CALLaccu_unsol_list_SEL2(Conn, dobj);           //  사원별/업종별통계(미해결내역서)
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
    public DOBJ CTLaccu_unsol_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccu_unsol_list_SEL1(Conn, dobj);           //  고소미해결내역서
        dobj  = CALLaccu_unsol_list_SEL2(Conn, dobj);           //  사원별/업종별통계(미해결내역서)
        return dobj;
    }
    // 고소미해결내역서
    public DOBJ CALLaccu_unsol_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_unsol_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_unsol_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TO_ZIP = dobj.getRetObject("S").getRecord().get("TO_ZIP");   //우편번호 검색 TO
        String   JUDG_CD = dobj.getRetObject("S").getRecord().get("JUDG_CD");   //판결 코드
        String   FROM_ZIP = dobj.getRetObject("S").getRecord().get("FROM_ZIP");   //우편번호 검색 FROM
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.ACCU_DAY  ,  TA.UPSO_CD  ,  TC.GRADNM  ,  TC.MONPRNCFEE  ,  TB.UPSO_NM  ,  DECODE(TA.COMPN_DAY,  NULL,  DECODE(TA.JUDG_CD,'2','기소중지',  '3','기소유예','4','구약식(벌금)','미해결'))  GUBUN  ,  TB.MNGEMSTR_NM  ,  TB.UPSO_PHON  ,  TA.START_YRMN||'  ~  '||TA.END_YRMN  YRMN  ,  TA.REQ_ORG_AMT  +  TA.REQ_ADDT_AMT  TOT_AMT  ,  TD.HAN_NM  STAFF_NM  FROM  GIBU.TBRA_ACCU  TA  ,  GIBU.TBRA_UPSO  TB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TINS_MST01  TD  WHERE  TB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TB.UPSO_NEW_ZIP  BETWEEN  :FROM_ZIP   \n";
        query +=" AND  :TO_ZIP   \n";
        query +=" AND  TA.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TA.ACCU_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  TC.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TD.STAFF_NUM(+)  =  TB.STAFF_CD   \n";
        query +=" AND  TA.COMPN_DAY  IS  NULL   \n";
        query +=" AND  DECODE(:JUDG_CD,'1',  DECODE(TA.JUDG_CD,  '2',  '0',  '3',  '0',  '4',  '0',  '1'),  '2',  DECODE(TA.JUDG_CD,  '2',  '1',  '0'),  '3',  DECODE(TA.JUDG_CD,  '3',  '1',  '0'),  '4',  DECODE(TA.JUDG_CD,  '4',  '1',  '0'),  DECODE(TA.COMPN_DAY,  NULL,  '1',  DECODE(TA.SATN_STAT,  '008',  '0',  '1')))  =  '1'  ORDER  BY  TA.ACCU_DAY,  TA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("TO_ZIP", TO_ZIP);               //우편번호 검색 TO
        sobj.setString("JUDG_CD", JUDG_CD);               //판결 코드
        sobj.setString("FROM_ZIP", FROM_ZIP);               //우편번호 검색 FROM
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 사원별/업종별통계(미해결내역서)
    public DOBJ CALLaccu_unsol_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_unsol_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_unsol_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TO_ZIP = dobj.getRetObject("S").getRecord().get("TO_ZIP");   //우편번호 검색 TO
        String   JUDG_CD = dobj.getRetObject("S").getRecord().get("JUDG_CD");   //판결 코드
        String   FROM_ZIP = dobj.getRetObject("S").getRecord().get("FROM_ZIP");   //우편번호 검색 FROM
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(TD.HAN_NM)  CNT  ,  SUM(NVL(TA.REQ_ORG_AMT,0)  +  NVL(TA.REQ_ADDT_AMT,0))  TOT_AMT  ,  TD.HAN_NM  NAME  ,  'S'  GBN  FROM  GIBU.TBRA_ACCU  TA  ,  GIBU.TBRA_UPSO  TB  ,  INSA.TINS_MST01  TD  WHERE  TB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TA.ACCU_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  TB.UPSO_NEW_ZIP  BETWEEN  :FROM_ZIP   \n";
        query +=" AND  :TO_ZIP   \n";
        query +=" AND  TA.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TB.STAFF_CD  =  TD.STAFF_NUM(+)   \n";
        query +=" AND  TA.COMPN_DAY  IS  NULL   \n";
        query +=" AND  DECODE(:JUDG_CD,'1',  DECODE(TA.JUDG_CD,  '2',  '0',  '3',  '0',  '4',  '0',  '1'),  '2',  DECODE(TA.JUDG_CD,  '2',  '1',  '0'),  '3',  DECODE(TA.JUDG_CD,  '3',  '1',  '0'),  '4',  DECODE(TA.JUDG_CD,  '4',  '1',  '0'),  DECODE(TA.COMPN_DAY,  NULL,  '1',  DECODE(TA.SATN_STAT,  '008',  '0',  '1')))  =  '1'  GROUP  BY  TD.HAN_NM  UNION  ALL   \n";
        query +=" SELECT  COUNT(TC.BSTYP_CD)  CNT  ,  SUM(NVL(TA.REQ_ORG_AMT,0)  +  NVL(TA.REQ_ADDT_AMT,0))  TOT_AMT  ,  DECODE(TRIM(TC.BSTYP_CD),'h','휴게음식','j','제과','k','유흥주점','l','단란주점','m','에어로빅','n','무도장','o','노래방','p','레스토랑','q','호텔/콘도','r','백화점'  ,  's','중계유선','t','지부영상','u','음악유선','v','무대공연','w','경마','x','유원시설','y','게임방')  NAME  ,  'B'  GBN  FROM  GIBU.TBRA_ACCU  TA  ,  GIBU.TBRA_UPSO  TB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TINS_MST01  TD  WHERE  TB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TB.UPSO_NEW_ZIP  BETWEEN  :FROM_ZIP   \n";
        query +=" AND  :TO_ZIP   \n";
        query +=" AND  TA.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TA.ACCU_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  TC.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TD.STAFF_NUM(+)  =  TB.STAFF_CD   \n";
        query +=" AND  TA.COMPN_DAY  IS  NULL   \n";
        query +=" AND  DECODE(:JUDG_CD,'1',  DECODE(TA.JUDG_CD,  '2',  '0',  '3',  '0',  '4',  '0',  '1'),  '2',  DECODE(TA.JUDG_CD,  '2',  '1',  '0'),  '3',  DECODE(TA.JUDG_CD,  '3',  '1',  '0'),  '4',  DECODE(TA.JUDG_CD,  '4',  '1',  '0'),  DECODE(TA.COMPN_DAY,  NULL,  '1',  DECODE(TA.SATN_STAT,  '008',  '0',  '1')))  =  '1'  GROUP  BY  TC.BSTYP_CD ";
        sobj.setSql(query);
        sobj.setString("TO_ZIP", TO_ZIP);               //우편번호 검색 TO
        sobj.setString("JUDG_CD", JUDG_CD);               //판결 코드
        sobj.setString("FROM_ZIP", FROM_ZIP);               //우편번호 검색 FROM
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$accu_unsol_list
    //##**$$end
}
