
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra99_r01
{
    public bra99_r01()
    {
    }
    //##**$$upso_cnt_select
    /*
    */
    public DOBJ CTLupso_cnt_select(DOBJ dobj)
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
            dobj  = CALLupso_cnt_select_SEL1(Conn, dobj);           //  업소건수확인
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
    public DOBJ CTLupso_cnt_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_cnt_select_SEL1(Conn, dobj);           //  업소건수확인
        return dobj;
    }
    // 업소건수확인
    public DOBJ CALLupso_cnt_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_cnt_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_cnt_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  'TBRA_ACCU'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_ACCU','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_ADDR_TEMP'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_ADDR_TEMP','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_ADDR_TEMP  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_ADDR_TEMP2'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_ADDR_TEMP2','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_ADDR_TEMP2  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_ADDR_TEMP3'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_ADDR_TEMP3','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_ADDR_TEMP3  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_ADDR_TEMP_ERR'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_ADDR_TEMP_ERR','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_ADDR_TEMP_ERR  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_ADDR_TEMP_ERR3'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_ADDR_TEMP_ERR3','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_ADDR_TEMP_ERR3  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_BILL_ISS_MNG'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_BILL_ISS_MNG','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_BILL_ISS_MNG  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_BPAP_MNG'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_BPAP_MNG','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_BPAP_MNG  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_BPAP_PRNT_HISTY'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_BPAP_PRNT_HISTY','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_BRANCHG_HISTY'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_BRANCHG_HISTY','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANCHG_HISTY  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_CONTR_TERM'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_CONTR_TERM','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_CONTR_TERM  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DEMD_AUTO'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DEMD_AUTO','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DEMD_AUTO_MM'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DEMD_AUTO_MM','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DEMD_AUTO_MM  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DEMD_AUTO_MM_TEST'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DEMD_AUTO_MM_TEST','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DEMD_AUTO_MM_TEST  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DEMD_AUTO_TEST'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DEMD_AUTO_TEST','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DEMD_AUTO_TEST  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DEMD_ERR'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DEMD_ERR','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DEMD_ERR  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DEMD_INDTN'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DEMD_INDTN','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DEMD_INDTN  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DEMD_OCR'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DEMD_OCR','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DEMD_OCR_MM'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DEMD_OCR_MM','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DEMD_OCR_MM_TEST'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DEMD_OCR_MM_TEST','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DEMD_OCR_MM_TEST  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DEMD_OCR_TEST'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DEMD_OCR_TEST','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DEMD_OCR_TEST  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DEMD_REPT'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DEMD_REPT','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DISP_HISTY'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DISP_HISTY','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DISP_HISTY  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_DLGTN_CLAIM'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_DLGTN_CLAIM','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_ETC_PAL'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_ETC_PAL','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_ETC_PAL  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_MNG_ZIP_TEMP'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_MNG_ZIP_TEMP','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_MNG_ZIP_TEMP  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_NOLEV_DISP_LIST'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_NOLEV_DISP_LIST','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_NOLEV_DISP_LIST  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_NOREBANG_INFO'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_NOREBANG_INFO','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_NOREBANG_INFO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_NOTE'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_NOTE','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_NOTE_BRANNULL_110921'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_NOTE_BRANNULL_110921','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE_BRANNULL_110921  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_OCR_PRNT_RSLT'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_OCR_PRNT_RSLT','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_OCR_PRNT_RSLT  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_OFF_UPSO_MNG'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_OFF_UPSO_MNG','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_OFF_UPSO_MNG  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_REPT'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_REPT','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_REPT_ACK_ISS'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_REPT_ACK_ISS','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT_ACK_ISS  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_REPT_AUTO'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_REPT_AUTO','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT_AUTO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_REPT_BALANCE'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_REPT_BALANCE','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_REPT_CARD'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_REPT_CARD','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT_CARD  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_REPT_ERR'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_REPT_ERR','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT_ERR  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_REPT_ERR1'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_REPT_ERR1','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT_ERR1  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_REPT_OCR'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_REPT_OCR','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT_OCR  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_REPT_RETURN'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_REPT_RETURN','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT_RETURN  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_REPT_RETURN_NOTE'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_REPT_RETURN_NOTE','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT_RETURN_NOTE  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_REPT_TRANS'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_REPT_TRANS','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT_TRANS  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_REPT_UPSO_DISTR'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_REPT_UPSO_DISTR','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSORTAL_INFO'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSORTAL_INFO','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_20110603'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_20110603','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_20110603  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_20110607'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_20110607','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_20110607  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_20110610'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_20110610','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_20110610  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_20110722'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_20110722','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_20110722  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_ACMCN_INFO'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_ACMCN_INFO','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_ACMCN_INFO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_ADRS_FILEINFO'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_ADRS_FILEINFO','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_ADRS_FILEINFO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_AUBRY_INFO'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_AUBRY_INFO','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUBRY_INFO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_AUTO'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_AUTO','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_AUTORSLT'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_AUTORSLT','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTORSLT  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_CLSED'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_CLSED','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_CLSED  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_ONOFF_INFO'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_ONOFF_INFO','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_UPDATE_AFTER'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_UPDATE_AFTER','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_UPDATE_AFTER  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_UPDATE_AFTER3'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_UPDATE_AFTER3','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_UPDATE_AFTER3  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_UPDATE_BEFORE'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_UPDATE_BEFORE','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_UPDATE_BEFORE  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_UPDATE_BEFORE3'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_UPDATE_BEFORE3','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_UPDATE_BEFORE3  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_VISIT'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_VISIT','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  'TBRA_UPSO_VISIT_BRE'  TABLE_NAME,  FIDU.GET_TABLE_INFO('1','GIBU',  'TBRA_UPSO_VISIT_BRE','')  T_NAME,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_VISIT_BRE  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  TABLE_NAME ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$upso_cnt_select
    //##**$$end
}
