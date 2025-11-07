package com.example.mall.aop;


import com.example.mall.pojo.entity.Log;
import com.example.mall.service.LogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.example.mall.pojo.vo.UserVO;



import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LogAspect{

    @Autowired
    private LogService logService;

    @Around("@annotation(RequiredLog)")//通知內直接綁定對應的切入點,就是通知跟切入點可以放在一起
    public Object doLog(ProceedingJoinPoint jp) throws Throwable {
        int status = 1;//狀態碼:1是默認執行成功
        long time = 0L;//初始化時間 結束時間-開始時間 = time
        String error = "";//用於紀錄異常信息(錯誤信息)
        long startTime = System.currentTimeMillis();//定義系統開始時間,在執行之前紀錄開始時間

        try {
            Object result = jp.proceed();//執行
            long endTime = System.currentTimeMillis();//定義系統結束時間,在執行結束時紀錄結束時間
            time = endTime - startTime;//例如:結束時間10秒-開始時間1秒 = time(9秒)(花了多少時間)
            return result;//執行結束

        } catch (Throwable e) {//如果異常,捕獲異常並紀錄是什麼異常,再拋出
            long errorEndTime = System.currentTimeMillis();//異常時間
            time = errorEndTime - startTime;//異常時間 - 開始時間
            status = 0; //狀態碼0,執行失敗
            error = e.getMessage();//e.獲取到的異常
            throw e;//拋出異常

        }finally {//try跟catch結束時一定會執行finally
            //封裝,插入數據庫日誌的動作封裝到一個新的方法裡面--調用即可
            saveLog(jp,time,status,error);//會去執行下面方法,連接點,執行時間,狀態碼,錯誤信息
        }
    }

    //封裝,插入數據庫日誌的動作封裝到一個新的方法裡面
    private void saveLog(ProceedingJoinPoint jp, long time, int status, String error) throws NoSuchMethodException, JsonProcessingException {
        String username = "";//用戶名
        String operation = "";//用戶操作
        String method = "";//方法
        String params = "";//參數
        String ip = "";//用戶ip

        //處理其餘字段
        //從請求中取得session對象
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();//請求正文,必須強轉,獲取請求對象
        HttpServletRequest request = requestAttributes.getRequest();//從對象中,獲取請求,就是取出原生的 HTTP 請求物件。
        HttpSession session = request.getSession();//透過request取得session。如果 session 不存在，則會自動建立一個。
        //再從session獲取user對象,強轉什麼類型以及類型,要看登入時set的類型,例如登入時set是userVO類(返回給前端的類)
        //就要強轉成userVO類
        UserVO user = (UserVO) session.getAttribute("sessionVO");
        if (user != null){//判斷session裡面是否有登入的用戶,確認session裡是否有使用者登入，若有，則取得該使用者的名稱。
            username = user.getName();//從session獲取,紀錄當前的登錄的用戶
        }
        ip = request.getRemoteAddr();//這行會取得用戶端發出HTTP請求時的IP。


        //反射機制:運作過程中,獲取對應的類,進行操作的機制
        //連接點運行的目標jp.getTarget() 獲取運行類的字節碼文件getClass()
        //想要獲取,操作信息,方法名子,方法參數,就要透過反射機制
        Class targetClass = jp.getTarget().getClass();//獲取運行方法對應類的字節碼文件

        //joinPoint.getSignature()可以獲取方法的簽名對象(方法名稱/方法的參數類型)
        MethodSignature signature = (MethodSignature)jp.getSignature();//獲取方法的簽名,這是一個接口不能直接用,必須強轉

        //通過反射targetClass的getDeclaredMethod方法
        //參數:取得使用中的 1.方法名signature.getName() 2.方法的傳入的參數類型signature.getParameterTypes()
        //可以讓你拿到方法的名稱、參數型別、返回型別等資訊。
        Method targetMethod = targetClass.getDeclaredMethod(signature.getName(), signature.getParameterTypes());

        //取得方法 參數
        //取得路徑名以及類名targetClass.getName() 強轉string"."取得方法名稱targetMethod.getName()
        method = targetClass.getName()+"."+targetMethod.getName();

        //取得參數 將jp.getArgs()轉換成String類型 new ObjectMapper().writeValueAsString()
        //params = new ObjectMapper().writeValueAsString(jp.getArgs());

        params = java.util.Arrays.stream(jp.getArgs())
                .map(arg -> {
                    if (arg == null) return null;
                    try {
                        return new ObjectMapper().writeValueAsString(arg); // 試著轉成 JSON 字串
                    } catch (Exception e) {
                        return arg.getClass().getSimpleName(); // 轉不動就只記類名，避免炸
                    }
                })
                .collect(java.util.stream.Collectors.toList())
                .toString();


        //4.取得操作(指的是 方法上註解中的value值)(反射)
        RequiredLog annotation = targetMethod.getAnnotation(RequiredLog.class);//取得自訂義註解的類
        operation = annotation.value();//就可以獲取到在方法上的value值內容(看圖片6)

        //去初始化其餘字段
        Log log = new Log();//日誌對象
        log.setTime(time);//設置執行時間
        log.setStatus(status);//設置狀態碼
        log.setError(error);//設置錯誤信息
        log.setCreatedTime(new Date());//設置創建時間
        log.setUsername(username);//設置用戶名
        log.setIp(ip);//設置用戶ip
        log.setOperation(operation);//設置用戶操做
        log.setMethod(method);//設置方法
        log.setParams(params);//設置參數
        logService.insert(log);//執行新增方法
    }
}
