
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_r14_1
{
    public bra01_r14_1()
    {
    }
    //##**$$sms_data_create
    /* * 프로그램명 : bra01_r14
    * 작성자 : 서정재
    * 작성일 : 2009/12/23
    * 설명 :
    각 지부별 기준년월 기준으로 미납된 청구 정보를 조회한다.
    미징수 리스트와 SELECT 하는 기준은 같으나 청구가 한번이상 된 업소 중에서 조회한다.
    핸드폰 정보가 있는 업소를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsms_data_create(DOBJ dobj)
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
            dobj  = CALLsms_data_create_SEL1(Conn, dobj);           //  SMS 대상리스트
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
    public DOBJ CTLsms_data_create( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsms_data_create_SEL1(Conn, dobj);           //  SMS 대상리스트
        return dobj;
    }
    // SMS 대상리스트
    // 미징수 개월수에 따른 전체 업소 리스트를 가져온다
    public DOBJ CALLsms_data_create_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsms_data_create_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsms_data_create_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //시작 월수
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //종료 월수
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.DEMD_MMCNT  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.MNGEMSTR_RESINUM  ,  NULL,  '',  'OK')  CHK_RESINUM  ,  XB.MNGEMSTR_NM  ,  DECODE(TRIM(XA.BSTYP_CD),  'o',  GIBU.FT_GET_NOREBANG_GRAD(XB.UPSO_CD),  XC.GRADNM)  GRADNM  ,  DECODE  ((SELECT  NVL(COUNT(UPSO_CD),  0)  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  :DEMD_YRMN  BETWEEN  START_YRMN   \n";
        query +=" AND  END_YRMN),  0,  '',  '채권')  BOND  ,  XA.MONPRNCFEE  ,  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  UPSO_ADDR  ,  XB.UPSO_PHON  ,  XB.MNGEMSTR_HPNUM  ,  XA.TOT_USE_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_USE_AMT  +  XA.TOT_ADDT_AMT  TOT_AMT  ,  DECODE(XB.BEFORE_UPSO_CD,  NULL,  '',  '재'  ||  TO_CHAR(XB.INS_DATE,  'YYYYMMDD'))  ||  '  '  ||  NVL(XD.GOSO_YN,  '')  REMAK  ,  XB.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,  SUBSTR(XA.START_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.START_YRMN,  5,  2)  START_YRMN  ,  SUBSTR(XA.END_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.END_YRMN,  5,  2)  END_YRMN  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  XB.STAFF_CD)  BRAN_PHON_NUM  FROM  (   \n";
        query +=" SELECT  DEMD_GBN  ,  UPSO_CD  ,  DEMD_YRMN  ,  MONPRNCFEE  ,  BSTYP_CD  ,  UPSO_GRAD  ,  DEMD_MMCNT  ,  START_YRMN  ,  END_YRMN  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :DEMD_YRMN,  :DEMD_YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  NEW_DAY  <=  :DEMD_YRMN  ||  '31')   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :DEMD_YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  )  )  XA  ,  GIBU.TBRA_UPSO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(GOSO_YN)  GOSO_YN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(COMPN_DAY,  NULL,  '고소중',  DECODE(JUDG_CD,  '2',  '기소중지',  '4',  '구약식',  NULL))  GOSO_YN  FROM  GIBU.TBRA_ACCU  WHERE  (JUDG_CD  NOT  IN  ('1','3','5','6','7','8','9','41','99')   \n";
        query +=" OR  JUDG_CD  IS  NULL)  )  GROUP  BY  UPSO_CD  )  XD  ,  INSA.TCPM_BIPLC  XE  WHERE  XB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XB.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  DEMD_YRMN  =  :DEMD_YRMN)   \n";
        query +=" AND  XB.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'  GROUP  BY  UPSO_CD  HAVING  COUNT(*)  >  0)   \n";
        query +=" AND  XB.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_ACCU  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL)   \n";
        query +=" AND  XB.UPSO_CD  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  BRAN_CD=  :BRAN_CD   \n";
        query +=" AND  DEMD_YRMN  =  :DEMD_YRMN)   \n";
        query +=" AND  XB.MNGEMSTR_HPNUM  IS  NOT  NULL   \n";
        query +=" AND  NVL(XB.PAPER_CANC_YN,'-')  <>  'Y'   \n";
        query +=" AND  SUBSTR(XB.MNGEMSTR_HPNUM,1,3)  IN  ('010','011','017','016','018','019')   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XA.UPSO_GRAD   \n";
        query +=" AND  XC.BSTYP_CD  =  XA.BSTYP_CD   \n";
        query +=" AND  XD.UPSO_CD  (+)  =  XA.UPSO_CD   \n";
        query +=" AND  XE.GIBU(+)  =  XB.BRAN_CD   \n";
        query +=" AND  (XB.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XB.CLSBS_INS_DAY,1,6),'  ')  >  :DEMD_YRMN)  ORDER  BY  XB.UPSO_NEW_ZIP,  XB.UPSO_NM ";
        sobj.setSql(query);
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //시작 월수
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //종료 월수
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$sms_data_create
    //##**$$end
}
