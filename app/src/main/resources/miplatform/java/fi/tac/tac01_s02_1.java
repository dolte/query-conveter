
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac01_s02_1
{
    public tac01_s02_1()
    {
    }
    //##**$$searchGibu_bill
    /* * 프로그램명 : tac01_s02
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchGibu_bill(DOBJ dobj)
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
            dobj  = CALLsearchGibu_bill_SEL1(Conn, dobj);           //  지부계산서조회
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
    public DOBJ CTLsearchGibu_bill( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchGibu_bill_SEL1(Conn, dobj);           //  지부계산서조회
        return dobj;
    }
    // 지부계산서조회
    // 계산서 종류 가 3,4인것만 조회
    public DOBJ CALLsearchGibu_bill_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchGibu_bill_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchGibu_bill_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   BILL_KND = dobj.getRetObject("S").getRecord().get("BILL_KND");   //계산서 종류
        String   BSCON_FNM_NM = dobj.getRetObject("S").getRecord().get("BSCON_FNM_NM");   //거래처 상호 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.DEMD_NUM, A.BILL_NUM, A.ISS_DAY, A.BILL_KND, B.CODE_NM AS BILL_KND_NM, A.BILL_GBN, C.CODE_NM AS BILL_GBN_NM, A.BSCON_CD, A.BIPLC_GBN, A.BRAN_CD, A.BSCON_NM, A.SUPPPRES_NM, A.BSCON_INS_NUM, A.BSCON_FNM_NM, A.BSCON_REPPRES_NM, A.BSCON_POST_NUM, A.BSCON_ADDR, A.BSCON_BSCDTN, A.BSCON_BSTYP, A.SUPPPRES_CD, A.SUPPPRES_INS_NUM, A.SUPPPRES_FNM_NM, A.SUPPPRES_REPPRES_NM, A.SUPPPRES_ADDR, A.SUPPPRES_BSCDTN, A.SUPPPRES_BSTYP, A.SUPPTEMP_AMT, A.ATAX_AMT, A.REMAK, A.ISS_YN, '1' EMAIL , NVL((  ";
        query +=" SELECT APPTN_GBN  ";
        query +=" FROM GIBU.TBRA_BILL_ISS_MNG  ";
        query +=" WHERE BILL_NUM =A.BILL_NUM),'1') APPTN_GBN, D.EMAIL_ADDR  ";
        query +=" FROM FIDU.TTAC_BILL A ,FIDU.TENV_CODE B , FIDU.TENV_CODE C, FIDU.TMEM_MB D  ";
        query +=" WHERE 1=1  ";
        query +=" AND B.HIGH_CD = '00283'  ";
        query +=" AND C.HIGH_CD = '00216'  ";
        query +=" AND A.BILL_KND = B.CODE_CD  ";
        query +=" AND A.BILL_GBN = C.CODE_CD  ";
        query +=" AND A.BSCON_CD =D.MB_CD(+)  ";
        query +=" AND A.DEL_YN IS NULL  ";
        query +=" AND SUBSTR(A.ISS_DAY,1,6) LIKE :DEMD_DAY ||'%'  ";
        query +=" AND A.BILL_KND IN(3,4)  ";
        if( !BSCON_FNM_NM.equals("") )
        {
            query +=" AND A.BSCON_FNM_NM LIKE '%'|| :BSCON_FNM_NM ||'%'  ";
        }
        query +=" AND A.BILL_KND = :BILL_KND  ";
        query +=" ORDER BY A.BRAN_CD,A.BSCON_INS_NUM  ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        if(!BSCON_FNM_NM.equals(""))
        {
            sobj.setString("BSCON_FNM_NM", BSCON_FNM_NM);               //거래처 상호 명
        }
        return sobj;
    }
    //##**$$searchGibu_bill
    //##**$$end
}
