
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra09_p05
{
    public bra09_p05()
    {
    }
    //##**$$nonpy_upso_cnt
    /*
    */
    public DOBJ CTLnonpy_upso_cnt(DOBJ dobj)
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
            dobj  = CALLnonpy_upso_cnt_SEL1(Conn, dobj);           //  통계조회
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
    public DOBJ CTLnonpy_upso_cnt( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnonpy_upso_cnt_SEL1(Conn, dobj);           //  통계조회
        return dobj;
    }
    // 통계조회
    public DOBJ CALLnonpy_upso_cnt_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLnonpy_upso_cnt_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_upso_cnt_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //시작 월수
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //종료 월수
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ATTE,  CITYCNTYDSRC,  COUNT(0)  AS  UPSO_CNT  FROM  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,   \n";
        query +=" (SELECT  ATTE  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =XB.UPSO_BD_MNG_NUM)  AS  ATTE  ,   \n";
        query +=" (SELECT  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =XB.UPSO_BD_MNG_NUM)  AS  CITYCNTYDSRC  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :DEMD_YRMN,  :DEMD_YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  UPSO_BD_MNG_NUM  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  NEW_DAY  <=  :DEMD_YRMN  ||  '31')   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :DEMD_YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  UNION  ALL   \n";
        query +=" SELECT  TB.UPSO_CD  ,  TB.OPBI_YRMN  ,  TB.OPBI_CNT  -  COUNT(TA.NOTE_YRMN)  DEMD_MMCNT  ,  :DEMD_YRMN  START_YRMN  FROM  GIBU.TBRA_NOTE  TA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  SUBSTR(A.OPBI_DAY,  1,  6)  OPBI_YRMN  ,  MONTHS_BETWEEN(TO_DATE(:DEMD_YRMN,  'YYYYMM'),  TO_DATE(SUBSTR(A.OPBI_DAY,  1,  6),  'YYYYMM'))  +  1  OPBI_CNT  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_NOTE  B  WHERE  (A.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(A.CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  (A.NEW_DAY  IS  NULL   \n";
        query +=" OR  A.NEW_DAY  <=  :DEMD_YRMN  ||  '31')   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.NOTE_YRMN  =  :DEMD_YRMN  )  TB  WHERE  TA.UPSO_CD  =  TB.UPSO_CD  GROUP  BY  TB.UPSO_CD,  TB.OPBI_YRMN,  TB.OPBI_CNT  HAVING  (TB.OPBI_CNT  -  COUNT(TA.NOTE_YRMN))  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  )  A  )  XA  ,  GIBU.TBRA_UPSO  XB  WHERE  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  GIBU.FT_SPLIT(XA.DEMDS,  ',',  7)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON   \n";
        query +=" AND  GIBU.FT_SPLIT(XA.DEMDS,  ',',  2)  =  DECODE(:BSTYP_CD,  NULL,  GIBU.FT_SPLIT(XA.DEMDS,  ',',  2),  'A',  GIBU.FT_SPLIT(XA.DEMDS,  ',',  2),  :BSTYP_CD)  )  group  by  ATTE,  CITYCNTYDSRC  ORDER  BY  ATTE,  CITYCNTYDSRC ";
        sobj.setSql(query);
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //시작 월수
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //종료 월수
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$nonpy_upso_cnt
    //##**$$end
}
