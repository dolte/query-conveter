
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s04_2
{
    public bra01_s04_2()
    {
    }
    //##**$$closed_mng_branch
    /* 본부직원용과 지부직원용의 차이
    지부직원은 과납대체를 제외한 나머지 휴업구분(휴업, 수리, 정지, 천재, 미확인, 사고, 기타)에 대해
    업소방문등록 휴폐업확인서에서 첨부파일이 필요하다.
    본부직원은 모든 휴업구분에 대해 첨부파일이 필요하지 않다.
    지부직원은 일반폐업에 대해
    업소방문등록 휴폐업확인서에서 첨부파일이 필요하다.
    본부직원은 모든 폐업구분에 대해 첨부파일이 필요하지 않다.
    */
    public DOBJ CTLclosed_mng_branch(DOBJ dobj)
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
            dobj  = CALLclosed_mng_branch_MIUD15(Conn, dobj);           //  관리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLclosed_mng_branch_SEL18(Conn, dobj);           //  휴업상세정보
            dobj  = CALLclosed_mng_branch_SEL45(Conn, dobj);           //  폐업상세정보
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
    public DOBJ CTLclosed_mng_branch( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLclosed_mng_branch_MIUD15(Conn, dobj);           //  관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLclosed_mng_branch_SEL18(Conn, dobj);           //  휴업상세정보
        dobj  = CALLclosed_mng_branch_SEL45(Conn, dobj);           //  폐업상세정보
        return dobj;
    }
    // 관리
    public DOBJ CALLclosed_mng_branch_MIUD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD15");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLclosed_mng_branch_SEL36(Conn, dobj);           //  분기
                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                {
                    dobj  = CALLclosed_mng_branch_SEL37(Conn, dobj);           //  폐업인경우입금여부확인
                    dobj  = CALLclosed_mng_branch_SEL38(Conn, dobj);           //  업소방문등록 확인
                    dobj  = CALLclosed_mng_branch_SEL79(Conn, dobj);           //  결재번호조회
                    if( dobj.getRetObject("SEL37").getRecord().getDouble("CNT") > 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="해당년월 혹은 이후에 입금내역이 존재합니다. 다시 확인해 주시기 바랍니다.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else if( dobj.getRetObject("R").getRecord().get("GBN").equals("01") && dobj.getRetObject("SEL38").getRecord().getDouble("CNT") == 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="업소방문등록의 폐업등록이 있고, 첨부파일이 한개 이상 필요합니다.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else if(dobj.getRetObject("R").getRecord().get("GBN").equals("01") && !dobj.getRetObject("SEL79").getRecord().get("SATN_YN").equals("Y"))
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="업소 휴폐업 사유서의 결재가 완료되지 않았습니다.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else
                    {
                        dobj  = CALLclosed_mng_branch_MRG38( dobj);        //  결과취합
                        dobj  = CALLclosed_mng_branch_SEL20(Conn, dobj);           //  CLSED_NUM생성
                        dobj  = CALLclosed_mng_branch_INS13(Conn, dobj);           //  휴폐업등록
                        if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                        {
                            dobj  = CALLclosed_mng_branch_SEL44(Conn, dobj);           //  입금월
                            dobj  = CALLclosed_mng_branch_MRG46( dobj);        //  입금월통합하기
                            dobj  = CALLclosed_mng_branch_XIUD49(Conn, dobj);           //  휴폐업입금등록
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_branch_XIUD22(Conn, dobj);           //  업소정보변경
                                dobj  = CALLclosed_mng_branch_OSP63(Conn, dobj);           //  채권업소체크
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_branch_OSP63(Conn, dobj);           //  채권업소체크
                            }
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_branch_SEL47(Conn, dobj);           //  입금월
                            dobj  = CALLclosed_mng_branch_MRG46( dobj);        //  입금월통합하기
                            dobj  = CALLclosed_mng_branch_XIUD49(Conn, dobj);           //  휴폐업입금등록
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_branch_XIUD22(Conn, dobj);           //  업소정보변경
                                dobj  = CALLclosed_mng_branch_OSP63(Conn, dobj);           //  채권업소체크
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_branch_OSP63(Conn, dobj);           //  채권업소체크
                            }
                        }
                    }
                }
                else
                {
                    dobj  = CALLclosed_mng_branch_SEL23(Conn, dobj);           //  입금여부확인
                    dobj  = CALLclosed_mng_branch_SEL24(Conn, dobj);           //  업소방문등록 확인
                    dobj  = CALLclosed_mng_branch_SEL81(Conn, dobj);           //  결재번호조회
                    if( dobj.getRetObject("SEL23").getRecord().getDouble("CNT") > 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="해당년월에 입금내역이 존재합니다. 다시 확인해 주시기 바랍니다.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else if( ( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("11") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("12") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("13") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("15") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("16") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("17") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("18") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("19") ) && dobj.getRetObject("SEL24").getRecord().getDouble("CNT") == 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="업소방문등록의 휴업등록이 있고, 첨부파일이 한개 이상 필요합니다.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else if(( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("11") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("12") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("13") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("15") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("16") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("17") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("18") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("19") ) && !dobj.getRetObject("SEL81").getRecord().get("SATN_YN").equals("Y"))
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="업소 휴폐업 사유서의 결재가 완료되지 않았습니다.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else
                    {
                        dobj  = CALLclosed_mng_branch_MRG38( dobj);        //  결과취합
                        dobj  = CALLclosed_mng_branch_SEL20(Conn, dobj);           //  CLSED_NUM생성
                        dobj  = CALLclosed_mng_branch_INS13(Conn, dobj);           //  휴폐업등록
                        if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                        {
                            dobj  = CALLclosed_mng_branch_SEL44(Conn, dobj);           //  입금월
                            dobj  = CALLclosed_mng_branch_MRG46( dobj);        //  입금월통합하기
                            dobj  = CALLclosed_mng_branch_XIUD49(Conn, dobj);           //  휴폐업입금등록
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_branch_XIUD22(Conn, dobj);           //  업소정보변경
                                dobj  = CALLclosed_mng_branch_OSP63(Conn, dobj);           //  채권업소체크
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_branch_OSP63(Conn, dobj);           //  채권업소체크
                            }
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_branch_SEL47(Conn, dobj);           //  입금월
                            dobj  = CALLclosed_mng_branch_MRG46( dobj);        //  입금월통합하기
                            dobj  = CALLclosed_mng_branch_XIUD49(Conn, dobj);           //  휴폐업입금등록
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_branch_XIUD22(Conn, dobj);           //  업소정보변경
                                dobj  = CALLclosed_mng_branch_OSP63(Conn, dobj);           //  채권업소체크
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_branch_OSP63(Conn, dobj);           //  채권업소체크
                            }
                        }
                    }
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLclosed_mng_branch_SEL51(Conn, dobj);           //  마감체크
                if( dobj.getRetObject("SEL51").getRecord().getDouble("CNT") > 0)
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        String message ="마감된 달입니다. 수정할수 없습니다.";
                        dobj.setRetmsg(message);
                        Conn.rollback();
                        return dobj;
                    }
                }
                else
                {
                    dobj  = CALLclosed_mng_branch_DEL48(Conn, dobj);           //  원장정보삭제
                    if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                    {
                        dobj  = CALLclosed_mng_branch_SEL55(Conn, dobj);           //  입금여부확인
                        dobj  = CALLclosed_mng_branch_SEL56(Conn, dobj);           //  업소방문등록 확인
                        if( dobj.getRetObject("SEL55").getRecord().getDouble("CNT") > 0)
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="해당년월 혹은 이후에 입금내역이 존재합니다. 다시 확인해 주시기 바랍니다.";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                        else if( dobj.getRetObject("R").getRecord().get("GBN").equals("01") && dobj.getRetObject("SEL56").getRecord().getDouble("CNT") == 0)
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="업소방문등록의 폐업등록이 있고, 첨부파일이 한개 이상 필요합니다.";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_branch_MRG56( dobj);        //  결과취합
                            dobj  = CALLclosed_mng_branch_UPD34(Conn, dobj);           //  휴업정보수정
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_branch_SEL60(Conn, dobj);           //  입금월
                                dobj  = CALLclosed_mng_branch_MRG61( dobj);        //  결과취합
                                dobj  = CALLclosed_mng_branch_XIUD50(Conn, dobj);           //  휴업(입금)등록
                                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                                {
                                    dobj  = CALLclosed_mng_branch_XIUD64(Conn, dobj);           //  업소정보변경
                                    dobj  = CALLclosed_mng_branch_OSP64(Conn, dobj);           //  채권업소체크
                                }
                                else
                                {
                                    dobj  = CALLclosed_mng_branch_OSP64(Conn, dobj);           //  채권업소체크
                                }
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_branch_SEL39(Conn, dobj);           //  입금월
                                dobj  = CALLclosed_mng_branch_MRG61( dobj);        //  결과취합
                                dobj  = CALLclosed_mng_branch_XIUD50(Conn, dobj);           //  휴업(입금)등록
                                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                                {
                                    dobj  = CALLclosed_mng_branch_XIUD64(Conn, dobj);           //  업소정보변경
                                    dobj  = CALLclosed_mng_branch_OSP64(Conn, dobj);           //  채권업소체크
                                }
                                else
                                {
                                    dobj  = CALLclosed_mng_branch_OSP64(Conn, dobj);           //  채권업소체크
                                }
                            }
                        }
                    }
                    else
                    {
                        dobj  = CALLclosed_mng_branch_SEL46(Conn, dobj);           //  입금여부확인
                        dobj  = CALLclosed_mng_branch_SEL48(Conn, dobj);           //  업소방문등록 확인
                        if( dobj.getRetObject("SEL46").getRecord().getDouble("CNT") > 0)
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="해당년월에 입금내역이 존재합니다. 다시 확인해 주시기 바랍니다.";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                        else if( ( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("11") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("12") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("13") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("15") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("16") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("17") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("18") || dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("19") ) && dobj.getRetObject("SEL48").getRecord().getDouble("CNT") == 0)
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="업소방문등록의 휴업등록이 있고, 첨부파일이 한개 이상 필요합니다.";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_branch_MRG56( dobj);        //  결과취합
                            dobj  = CALLclosed_mng_branch_UPD34(Conn, dobj);           //  휴업정보수정
                            if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                            {
                                dobj  = CALLclosed_mng_branch_SEL60(Conn, dobj);           //  입금월
                                dobj  = CALLclosed_mng_branch_MRG61( dobj);        //  결과취합
                                dobj  = CALLclosed_mng_branch_XIUD50(Conn, dobj);           //  휴업(입금)등록
                                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                                {
                                    dobj  = CALLclosed_mng_branch_XIUD64(Conn, dobj);           //  업소정보변경
                                    dobj  = CALLclosed_mng_branch_OSP64(Conn, dobj);           //  채권업소체크
                                }
                                else
                                {
                                    dobj  = CALLclosed_mng_branch_OSP64(Conn, dobj);           //  채권업소체크
                                }
                            }
                            else
                            {
                                dobj  = CALLclosed_mng_branch_SEL39(Conn, dobj);           //  입금월
                                dobj  = CALLclosed_mng_branch_MRG61( dobj);        //  결과취합
                                dobj  = CALLclosed_mng_branch_XIUD50(Conn, dobj);           //  휴업(입금)등록
                                if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                                {
                                    dobj  = CALLclosed_mng_branch_XIUD64(Conn, dobj);           //  업소정보변경
                                    dobj  = CALLclosed_mng_branch_OSP64(Conn, dobj);           //  채권업소체크
                                }
                                else
                                {
                                    dobj  = CALLclosed_mng_branch_OSP64(Conn, dobj);           //  채권업소체크
                                }
                            }
                        }
                    }
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLclosed_mng_branch_SEL49(Conn, dobj);           //  마감체크
                if( dobj.getRetObject("SEL49").getRecord().getDouble("CNT") > 0)
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        String message ="마감된 달입니다. 수정할수 없습니다.";
                        dobj.setRetmsg(message);
                        Conn.rollback();
                        return dobj;
                    }
                }
                else
                {
                    dobj  = CALLclosed_mng_branch_SEL54(Conn, dobj);           //  이후금액존재체크
                    if( dobj.getRetObject("SEL54").getRecord().getDouble("CNT") > 0)
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="삭제하고자 하는 정보 이후의 입금내역이 존재합니다.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else
                    {
                        dobj  = CALLclosed_mng_branch_DEL49(Conn, dobj);           //  휴페업정보삭제
                        dobj  = CALLclosed_mng_branch_XIUD63(Conn, dobj);           //  원장삭제
                        if( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14"))
                        {
                            dobj  = CALLclosed_mng_branch_XIUD55(Conn, dobj);           //  업소정보변경
                            dobj  = CALLclosed_mng_branch_OSP65(Conn, dobj);           //  채권업소체크
                        }
                        else
                        {
                            dobj  = CALLclosed_mng_branch_OSP65(Conn, dobj);           //  채권업소체크
                        }
                    }
                }
            }
        }
        return dobj;
    }
    // 마감체크
    // 휴업일자(CLSED_DAY)에 해당하는 마감내역이 있는지 확인한다
    public DOBJ CALLclosed_mng_branch_SEL49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL49");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL49");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL49(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL49");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CLSED_DAY ="";        //휴업 일자
        String   CLSED_DAY_1 = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //휴업 일자
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("CLSED_BRAN");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  ,  ''  AS  DISTR_SEQ  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(SUBSTR(:CLSED_DAY_1,0,6),1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(SUBSTR(:CLSED_DAY_1,0,6),5,2)  =  END_MON   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY_1", CLSED_DAY_1);               //휴업 일자
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 마감체크
    // 휴업일자(CLSED_DAY)에 해당하는 마감내역이 있는지 확인한다
    public DOBJ CALLclosed_mng_branch_SEL51(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL51");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL51");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL51(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL51");
        rvobj.Println("SEL51");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL51(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CLSED_DAY ="";        //휴업 일자
        String   CLSED_DAY_1 = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //휴업 일자
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("CLSED_BRAN");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(SUBSTR(:CLSED_DAY_1,0,6),1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(SUBSTR(:CLSED_DAY_1,0,6),5,2)  =  END_MON   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY_1", CLSED_DAY_1);               //휴업 일자
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 분기
    public DOBJ CALLclosed_mng_branch_SEL36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL36");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL36");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL36(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL36");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  GBN  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 폐업인경우입금여부확인
    // 폐업하고자 하는 달에 기존에 입금이 등록되어 있는지 확인한다.
    public DOBJ CALLclosed_mng_branch_SEL37(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL37");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL37");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL37(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL37");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL37(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //시작년월
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  >=  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 이후금액존재체크
    // 삭제하고자 하는 휴업/폐업 다음에 입금내역이 존재하는지 체크한다 폐업인 경우 휴업 삭제는 가능해야 하므로 폐업은 제외한다
    public DOBJ CALLclosed_mng_branch_SEL54(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL54");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL54");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL54(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL54");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL54(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");   //입금 번호
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  NOTE_YRMN  >  (   \n";
        query +=" SELECT  MAX(NOTE_YRMN)  END_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM  )   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_GBN  <>  '14' ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 원장정보삭제
    // 기존 원장내역을 삭제한다.
    public DOBJ CALLclosed_mng_branch_DEL48(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL48");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_DEL48(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL48") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_DEL48(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");   //입금 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NOTE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        return sobj;
    }
    // 업소방문등록 확인
    // 업소방문등록의 폐업등록이 있고, 첨부파일이 한개 이상 있어야한다
    public DOBJ CALLclosed_mng_branch_SEL38(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL38");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL38");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL38(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL38");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL38(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //시작년월
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(B.MNG_NUM)  CNT  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_CONFIRM_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.GBN  =  '2'   \n";
        query +=" AND  A.START_YRMN  =  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 결재번호조회
    public DOBJ CALLclosed_mng_branch_SEL79(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL79");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL79");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL79(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL79");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL79(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //시작년월
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(SATN_YN,  'N')  AS  SATN_YN  FROM  GIBU.TBRA_CONFIRM_DOC  A  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.GBN  =  '2'   \n";
        query +=" AND  A.START_YRMN  =  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 입금여부확인
    // 수정하고자 하는 월에 이미 입금정보가 있는지 확인한다.
    public DOBJ CALLclosed_mng_branch_SEL55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL55");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL55");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL55(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL55");
        rvobj.Println("SEL55");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL55(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //시작년월
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  >=  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 휴페업정보삭제
    // 휴업 또는 폐업 정보를 삭제한다.
    public DOBJ CALLclosed_mng_branch_DEL49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL49");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_DEL49(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL49") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_DEL49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLSED_DAY = dvobj.getRecord().get("CLSED_DAY");   //휴업 일자
        String   CLSED_NUM = dvobj.getRecord().get("CLSED_NUM");   //휴업 번호
        String   CLSED_BRAN = dvobj.getRecord().get("CLSED_BRAN");   //휴업 지부
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_CLSED  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and CLSED_BRAN=:CLSED_BRAN  \n";
        query +=" and CLSED_NUM=:CLSED_NUM  \n";
        query +=" and CLSED_DAY=:CLSED_DAY";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //휴업 일자
        sobj.setString("CLSED_NUM", CLSED_NUM);               //휴업 번호
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //휴업 지부
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소방문등록 확인
    // 업소방문등록의 폐업등록이 있고, 첨부파일이 한개 이상 있어야한다
    public DOBJ CALLclosed_mng_branch_SEL56(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL56");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL56");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL56(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL56");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL56(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //시작년월
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(B.MNG_NUM)  CNT  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_CONFIRM_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.GBN  =  '2'   \n";
        query +=" AND  A.START_YRMN  =  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 원장삭제
    public DOBJ CALLclosed_mng_branch_XIUD63(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD63");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_XIUD63(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD63");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_XIUD63(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLSED_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //휴업 일자
        String   CLSED_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");   //휴업 구분
        String   CLSED_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");   //휴업 번호
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_NOTE  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND REPT_DAY = :CLSED_DAY  \n";
        query +=" AND REPT_NUM = :CLSED_NUM  \n";
        query +=" AND REPT_GBN = :CLSED_GBN";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //휴업 일자
        sobj.setString("CLSED_GBN", CLSED_GBN);               //휴업 구분
        sobj.setString("CLSED_NUM", CLSED_NUM);               //휴업 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소정보변경
    // 폐업처리된 업소를 원복시켜준다
    public DOBJ CALLclosed_mng_branch_XIUD55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD55");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_XIUD55(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD55");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_XIUD55(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET CLSBS_YRMN = '' , CLSBS_INS_DAY = '' , MOD_DATE = SYSDATE , MODPRES_ID = :MODPRES_ID  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 채권업소체크
    // 휴업/폐업등록으로 인해 채권기간이 메꿔지면 채권완료로 정보를 수정해준다
    public DOBJ CALLclosed_mng_branch_OSP65(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP65");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        String[]  incolumns ={"GBN","UPSO_CD","COMIS","CLSED_DAY","CLSED_NUM","CLSED_GBN","DISTR_SEQ","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CLSED_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");         //휴업 번호
            record.put("CLSED_NUM",CLSED_NUM);
            ////
            double   COMIS = 0;         //수수료
            record.put("COMIS",COMIS+"");
            ////
            String   DISTR_SEQ = dobj.getRetObject("SEL49").getRecord().get("DISTR_SEQ");         //분배 순번
            record.put("DISTR_SEQ",DISTR_SEQ);
            ////
            String   GBN ="D";         //구분
            record.put("GBN",GBN);
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //등록자 ID
            record.put("INSPRES_ID",INSPRES_ID);
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_CLAIM_COMPN_CHECK";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP65");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 결과취합
    // 휴업또는 폐업의 입금월의 최종정보를 취합한다.
    public DOBJ CALLclosed_mng_branch_MRG38(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG38");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL23, SEL37","CNT");
        rvobj.setName("MRG38") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 결과취합
    public DOBJ CALLclosed_mng_branch_MRG56(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG56");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL55, SEL46","CNT");
        rvobj.setName("MRG56") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // CLSED_NUM생성
    // 휴/폐업 일련번호(4자리)를 생성한다.
    public DOBJ CALLclosed_mng_branch_SEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL20");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL20");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL20(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLSED_BRAN = dobj.getRetObject("R").getRecord().get("CLSED_BRAN");   //휴업 지부
        String   CLSED_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //휴업 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(CLSED_NUM),  0)  +  1,  5,  '0')  CLSED_NUM  FROM  GIBU.TBRA_UPSO_CLSED  WHERE  CLSED_BRAN  =  :CLSED_BRAN   \n";
        query +=" AND  CLSED_DAY  =  :CLSED_DAY ";
        sobj.setSql(query);
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //휴업 지부
        sobj.setString("CLSED_DAY", CLSED_DAY);               //휴업 일자
        return sobj;
    }
    // 휴업정보수정
    // 기존 휴폐업정보를 수정한다.
    public DOBJ CALLclosed_mng_branch_UPD34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD34");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_UPD34(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD34") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_UPD34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //종료년월
        String MOD_DATE ="";        //등록 일시
        String START_YRMN ="";        //시작년월
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //종료일
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //종료년월
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   START_DAY = dvobj.getRecord().get("START_DAY");   //시작일
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        String   CLSED_DAY = dvobj.getRecord().get("CLSED_DAY");   //휴업 일자
        String   CLSED_NUM = dvobj.getRecord().get("CLSED_NUM");   //휴업 번호
        String   CLSED_BRAN = dvobj.getRecord().get("CLSED_BRAN");   //휴업 지부
        String   STTNT_DAY = dvobj.getRecord().get("STTNT_DAY");   //신고 일자
        String  CLSED_GBN="";          //휴업 구분
        if( ( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14") ))
        {
            CLSED_GBN = dobj.getRetObject("R").getRecord().get("GBN");
        }
        else
        {
            CLSED_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");
        }
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_CLSED  \n";
        query +=" set STTNT_DAY=:STTNT_DAY , MODPRES_ID=:MODPRES_ID , START_YRMN=SUBSTR(:START_YRMN_1, 1, 6) , START_DAY=:START_DAY , MOD_DATE=SYSDATE , END_YRMN=SUBSTR(:END_YRMN_1, 1, 6) , CLSED_GBN=:CLSED_GBN , REMAK=:REMAK , END_DAY=:END_DAY  \n";
        query +=" where CLSED_BRAN=:CLSED_BRAN  \n";
        query +=" and CLSED_NUM=:CLSED_NUM  \n";
        query +=" and CLSED_DAY=:CLSED_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //종료일
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("END_YRMN_1", END_YRMN_1);               //종료년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setString("CLSED_DAY", CLSED_DAY);               //휴업 일자
        sobj.setString("CLSED_NUM", CLSED_NUM);               //휴업 번호
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //휴업 지부
        sobj.setString("STTNT_DAY", STTNT_DAY);               //신고 일자
        sobj.setString("CLSED_GBN", CLSED_GBN);               //휴업 구분
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 휴폐업등록
    // 정보를 등록한다.
    public DOBJ CALLclosed_mng_branch_INS13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS13");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_INS13(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS13") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_INS13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //종료년월
        String INS_DATE ="";        //등록 일시
        String START_YRMN ="";        //시작년월
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //종료일
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //종료년월
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   START_DAY = dvobj.getRecord().get("START_DAY");   //시작일
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        String   CLSED_DAY = dvobj.getRecord().get("CLSED_DAY");   //휴업 일자
        String   CLSED_BRAN = dvobj.getRecord().get("CLSED_BRAN");   //휴업 지부
        String   STTNT_DAY = dvobj.getRecord().get("STTNT_DAY");   //신고 일자
        String  CLSED_GBN="";          //휴업 구분
        if( ( dobj.getRetObject("R").getRecord().get("CLSED_GBN").equals("14") ))
        {
            CLSED_GBN = dobj.getRetObject("R").getRecord().get("GBN");
        }
        else
        {
            CLSED_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");
        }
        String   CLSED_NUM = dobj.getRetObject("SEL20").getRecord().get("CLSED_NUM");   //휴업 번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_CLSED (STTNT_DAY, INSPRES_ID, CLSED_BRAN, CLSED_NUM, CLSED_DAY, START_YRMN, INS_DATE, START_DAY, UPSO_CD, END_YRMN, CLSED_GBN, REMAK, END_DAY)  \n";
        query +=" values(:STTNT_DAY , :INSPRES_ID , :CLSED_BRAN , :CLSED_NUM , :CLSED_DAY , SUBSTR(:START_YRMN_1, 1, 6), SYSDATE, :START_DAY , :UPSO_CD , SUBSTR(:END_YRMN_1, 1, 6), :CLSED_GBN , :REMAK , :END_DAY )";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //종료일
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("END_YRMN_1", END_YRMN_1);               //종료년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setString("CLSED_DAY", CLSED_DAY);               //휴업 일자
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //휴업 지부
        sobj.setString("STTNT_DAY", STTNT_DAY);               //신고 일자
        sobj.setString("CLSED_GBN", CLSED_GBN);               //휴업 구분
        sobj.setString("CLSED_NUM", CLSED_NUM);               //휴업 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 입금월
    // 폐업의 원장에 등록할 월을 조회한다.
    public DOBJ CALLclosed_mng_branch_SEL60(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL60");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL60");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL60(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL60");
        rvobj.Println("SEL60");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL60(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //시작년월
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  ,  ''  AS  DISTR_SEQ  FROM  (   \n";
        query +=" SELECT  YYYY  ||  MM  YRMN  FROM  GIBU.COPY_YY  YY  ,  GIBU.COPY_MM  MM  )  WHERE  YRMN  =  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        return sobj;
    }
    // 입금월
    // 폐업의 원장에 등록할 월을 조회한다.
    public DOBJ CALLclosed_mng_branch_SEL44(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL44");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL44");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL44(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL44");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL44(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //시작년월
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  ,  ''  AS  DISTR_SEQ  FROM  (   \n";
        query +=" SELECT  YYYY  ||  MM  YRMN  FROM  GIBU.COPY_YY  YY  ,  GIBU.COPY_MM  MM  )  WHERE  YRMN  =  SUBSTR(:START_YRMN_1,  1,  6) ";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        return sobj;
    }
    // 결과취합
    public DOBJ CALLclosed_mng_branch_MRG61(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG61");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL39, SEL60","YRMN, DISTR_SEQ");
        rvobj.setName("MRG61") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 입금월통합하기
    // 휴업 또는 폐업의 원장에 등록한 입금월 정보를 최종적으로 확인한다.
    public DOBJ CALLclosed_mng_branch_MRG46(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG46");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL47, SEL44","YRMN, DISTR_SEQ");
        rvobj.setName("MRG46") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 휴업(입금)등록
    // 휴업인경우: FROM~TO 기간동안 휴업으로 입금처리를 해준다
    public DOBJ CALLclosed_mng_branch_XIUD50(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD50");
        VOBJ dvobj = dobj.getRetObject("MRG61");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_XIUD50(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD50");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_XIUD50(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String NOTE_NUM ="";        //NOTE_NUM
        String   NOTE_NUM_2 = dobj.getRetObject("MRG61").getRecord().get("YRMN");   //원장 번호
        String   NOTE_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //원장 번호
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //지부 코드
        String   CLSED_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");   //휴업 번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //입금일자
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");   //입금 구분
        String   REPT_YRMN = dvobj.getRecord().get("YRMN");   //입금 년월
        String   STTNT_DAY = dobj.getRetObject("R").getRecord().get("STTNT_DAY");   //신고 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_NOTE (UPSO_CD, NOTE_YRMN, NOTE_NUM, REPT_DAY, REPT_NUM, REPT_GBN, USE_AMT, RECV_DAY, REMAK, INSPRES_ID, INS_DATE, BRAN_CD)  \n";
        query +=" VALUES (:UPSO_CD, :REPT_YRMN, (SELECT LPAD(NVL(MAX(NOTE_NUM), 0) + 1, 4, '0') NOTE_NUM FROM GIBU.TBRA_NOTE WHERE UPSO_CD = :NOTE_NUM_1 AND NOTE_YRMN = :NOTE_NUM_2), :REPT_DAY, :CLSED_NUM, :REPT_GBN, 0, :STTNT_DAY, :REMAK, :INSPRES_ID, SYSDATE, :BRAN_CD)";
        sobj.setSql(query);
        sobj.setString("NOTE_NUM_2", NOTE_NUM_2);               //원장 번호
        sobj.setString("NOTE_NUM_1", NOTE_NUM_1);               //원장 번호
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("CLSED_NUM", CLSED_NUM);               //휴업 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("STTNT_DAY", STTNT_DAY);               //신고 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 휴폐업입금등록
    // 원장테이블(TBRA_NOTE) 에 정보를 등록한다. 휴업인경우: FROM~TO 기간동안 휴업으로 입금처리를 해준다
    public DOBJ CALLclosed_mng_branch_XIUD49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD49");
        VOBJ dvobj = dobj.getRetObject("MRG46");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD49");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_XIUD49(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD49");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_XIUD49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String NOTE_NUM ="";        //NOTE_NUM
        String   NOTE_NUM_2 = dobj.getRetObject("MRG46").getRecord().get("YRMN");   //원장 번호
        String   NOTE_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //원장 번호
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //지부 코드
        String   CLSED_NUM = dobj.getRetObject("SEL20").getRecord().get("CLSED_NUM");   //휴업 번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //입금일자
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");   //입금 구분
        String   REPT_YRMN = dobj.getRetObject("MRG46").getRecord().get("YRMN");   //입금 년월
        String   STTNT_DAY = dobj.getRetObject("R").getRecord().get("STTNT_DAY");   //신고 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_NOTE (UPSO_CD, NOTE_YRMN, NOTE_NUM, REPT_DAY, REPT_NUM, REPT_GBN, USE_AMT, RECV_DAY, REMAK, INSPRES_ID, INS_DATE, BRAN_CD)  \n";
        query +=" VALUES (:UPSO_CD, :REPT_YRMN, (SELECT LPAD(NVL(MAX(NOTE_NUM), 0) + 1, 4, '0') NOTE_NUM FROM GIBU.TBRA_NOTE WHERE UPSO_CD = :NOTE_NUM_1 AND NOTE_YRMN = :NOTE_NUM_2), :REPT_DAY, :CLSED_NUM, :REPT_GBN, 0, :STTNT_DAY, :REMAK, :INSPRES_ID, SYSDATE, :BRAN_CD)";
        sobj.setSql(query);
        sobj.setString("NOTE_NUM_2", NOTE_NUM_2);               //원장 번호
        sobj.setString("NOTE_NUM_1", NOTE_NUM_1);               //원장 번호
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("CLSED_NUM", CLSED_NUM);               //휴업 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("STTNT_DAY", STTNT_DAY);               //신고 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소정보변경
    // 폐업인 경우 폐업정보를 업소테이블에 등록한다.
    public DOBJ CALLclosed_mng_branch_XIUD64(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD64");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_XIUD64(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD64");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_XIUD64(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //시작년월
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   STTNT_DAY = dobj.getRetObject("R").getRecord().get("STTNT_DAY");   //신고 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET CLSBS_YRMN = SUBSTR(:START_YRMN_1, 1, 6) , CLSBS_INS_DAY = :STTNT_DAY , BILL_ISS_YN = '0' , MOD_DATE = SYSDATE , MODPRES_ID = :MODPRES_ID  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("STTNT_DAY", STTNT_DAY);               //신고 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소정보변경
    // 폐업인 경우 폐업정보를 업소테이블에 등록한다.
    public DOBJ CALLclosed_mng_branch_XIUD22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD22");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("XIUD22");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_mng_branch_XIUD22(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD22");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_XIUD22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String START_YRMN ="";        //시작년월
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //시작년월
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   STTNT_DAY = dobj.getRetObject("R").getRecord().get("STTNT_DAY");   //신고 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET CLSBS_YRMN = SUBSTR(:START_YRMN_1, 1, 6) , CLSBS_INS_DAY = :STTNT_DAY , BILL_ISS_YN = '0' , MOD_DATE = SYSDATE , MODPRES_ID = :MODPRES_ID  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //시작년월
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("STTNT_DAY", STTNT_DAY);               //신고 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 채권업소체크
    // 휴업/폐업등록으로 인해 채권기간이 메꿔지면 채권완료로 정보를 수정해준다
    public DOBJ CALLclosed_mng_branch_OSP64(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP64");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        String[]  incolumns ={"GBN","UPSO_CD","COMIS","CLSED_DAY","CLSED_NUM","CLSED_GBN","DISTR_SEQ","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CLSED_NUM = dobj.getRetObject("R").getRecord().get("CLSED_NUM");         //휴업 번호
            record.put("CLSED_NUM",CLSED_NUM);
            ////
            double   COMIS = 0;         //수수료
            record.put("COMIS",COMIS+"");
            ////
            String   DISTR_SEQ = dobj.getRetObject("MRG61").getRecord().get("DISTR_SEQ");         //분배 순번
            record.put("DISTR_SEQ",DISTR_SEQ);
            ////
            String   GBN ="U";         //구분
            record.put("GBN",GBN);
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //등록자 ID
            record.put("INSPRES_ID",INSPRES_ID);
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_CLAIM_COMPN_CHECK";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP64");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 채권업소체크
    // 휴업/폐업등록으로 인해 채권기간이 메꿔지면 채권완료로 정보를 수정해준다
    public DOBJ CALLclosed_mng_branch_OSP63(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP63");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        String[]  incolumns ={"GBN","UPSO_CD","COMIS","CLSED_DAY","CLSED_NUM","CLSED_GBN","DISTR_SEQ","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CLSED_NUM = dobj.getRetObject("SEL20").getRecord().get("CLSED_NUM");         //휴업 번호
            record.put("CLSED_NUM",CLSED_NUM);
            ////
            double   COMIS = 0;         //수수료
            record.put("COMIS",COMIS+"");
            ////
            String   DISTR_SEQ = dobj.getRetObject("MRG46").getRecord().get("DISTR_SEQ");         //분배 순번
            record.put("DISTR_SEQ",DISTR_SEQ);
            ////
            String   GBN ="I";         //구분
            record.put("GBN",GBN);
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //등록자 ID
            record.put("INSPRES_ID",INSPRES_ID);
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_CLAIM_COMPN_CHECK";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP63");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 입금월
    // 휴업하고자 하는 월의 정보를 가져온다.
    public DOBJ CALLclosed_mng_branch_SEL39(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL39");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL39");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL39(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL39");
        rvobj.Println("SEL39");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL39(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("R").getRecord().get("START_DAY");   //시작일
        String   END_DAY = dobj.getRetObject("R").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MON  AS  YRMN  ,  ''  AS  DISTR_SEQ  FROM  INSA.TDUT_CALENDAR  WHERE  YRMNDAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  GROUP  BY  MON  HAVING  COUNT(1)  >  DECODE(TO_CHAR(LAST_DAY(TO_DATE(:START_DAY,  'YYYYMMDD')),  'DD'),  '31',  21,  '30',  20,  '29',  19,  '28',  18)  ORDER  BY  MON ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 입금월
    // 휴업의 원장에 등록할 월을 조회한다.
    public DOBJ CALLclosed_mng_branch_SEL47(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL47");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL47");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL47(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL47");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL47(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("R").getRecord().get("START_DAY");   //시작일
        String   END_DAY = dobj.getRetObject("R").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MON  AS  YRMN  ,  ''  AS  DISTR_SEQ  FROM  INSA.TDUT_CALENDAR  WHERE  YRMNDAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  GROUP  BY  MON  HAVING  COUNT(1)  >  DECODE(TO_CHAR(LAST_DAY(TO_DATE(:START_DAY,  'YYYYMMDD')),  'DD'),  '31',  21,  '30',  20,  '29',  19,  '28',  18)  ORDER  BY  MON ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 입금여부확인
    // 수정하고자 하는 월에 이미 입금정보가 있는지 확인한다.
    public DOBJ CALLclosed_mng_branch_SEL46(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL46");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL46");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL46(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL46");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL46(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("R").getRecord().get("START_DAY");   //시작일
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   END_DAY = dobj.getRetObject("R").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  IN  (   \n";
        query +=" SELECT  MON  AS  YRMN  FROM  INSA.TDUT_CALENDAR  WHERE  YRMNDAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  GROUP  BY  MON  ) ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 입금여부확인
    // 휴업하고자 하는 월에 기존에 입금이 되어 있는지 확인한다.
    public DOBJ CALLclosed_mng_branch_SEL23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL23");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL23");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL23(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL23");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("R").getRecord().get("START_DAY");   //시작일
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   END_DAY = dobj.getRetObject("R").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  IN  (   \n";
        query +=" SELECT  MON  AS  YRMN  FROM  INSA.TDUT_CALENDAR  WHERE  YRMNDAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  GROUP  BY  MON  ) ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 업소방문등록 확인
    // 업소방문등록의 휴업등록이 있고, 첨부파일이 한개 이상 있어야한다
    public DOBJ CALLclosed_mng_branch_SEL48(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL48");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL48");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL48(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL48");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL48(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("R").getRecord().get("START_DAY");   //시작일
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   END_DAY = dobj.getRetObject("R").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(B.MNG_NUM)  AS  CNT  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_CONFIRM_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.GBN  =  '1'   \n";
        query +=" AND  A.START_DAY  =  :START_DAY   \n";
        query +=" AND  A.END_DAY  =  :END_DAY ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 업소방문등록 확인
    // 업소방문등록의 휴업등록이 있고, 첨부파일이 한개 이상 있어야한다
    public DOBJ CALLclosed_mng_branch_SEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL24");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL24");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL24(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL24");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("R").getRecord().get("START_DAY");   //시작일
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   END_DAY = dobj.getRetObject("R").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(B.MNG_NUM)  AS  CNT  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_CONFIRM_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.GBN  =  '1'   \n";
        query +=" AND  A.START_DAY  =  :START_DAY   \n";
        query +=" AND  A.END_DAY  =  :END_DAY ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 결재번호조회
    public DOBJ CALLclosed_mng_branch_SEL81(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL81");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL81");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL81(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL81");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL81(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("R").getRecord().get("START_DAY");   //시작일
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   END_DAY = dobj.getRetObject("R").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(SATN_YN,  'N')  AS  SATN_YN  FROM  GIBU.TBRA_CONFIRM_DOC  A  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.GBN  =  '1'   \n";
        query +=" AND  A.START_DAY  =  :START_DAY   \n";
        query +=" AND  A.END_DAY  =  :END_DAY ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 휴업상세정보
    // 휴업정보를 조회한다.
    public DOBJ CALLclosed_mng_branch_SEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL18");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL18");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL18(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL18");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.CLSED_DAY  ,  A.CLSED_NUM  ,  A.CLSED_BRAN  ,  A.CLSED_GBN  ,  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  A.STTNT_DAY  ,  A.REMAK  ,  B.BRAN_CD  FROM  GIBU.TBRA_UPSO_CLSED  A,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  CLSED_GBN  NOT  IN  ('14','01','02','03','04') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 폐업상세정보
    // 폐업정보를 조회한다.
    public DOBJ CALLclosed_mng_branch_SEL45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL45");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL45");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_mng_branch_SEL45(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL45");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_mng_branch_SEL45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.CLSED_DAY  ,  A.CLSED_NUM  ,  A.CLSED_BRAN  ,  '14'  AS  CLSED_GBN  ,  A.CLSED_GBN  AS  GBN  ,  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  A.STTNT_DAY  ,  A.REMAK  ,  B.BRAN_CD  FROM  GIBU.TBRA_UPSO_CLSED  A,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  CLSED_GBN  IN  ('14','01','02','03','04') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$closed_mng_branch
    //##**$$end
}
