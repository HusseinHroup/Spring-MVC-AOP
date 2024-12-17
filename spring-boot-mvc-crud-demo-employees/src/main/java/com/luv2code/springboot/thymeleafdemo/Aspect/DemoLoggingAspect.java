package com.luv2code.springboot.thymeleafdemo.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.annotations.Bag;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class DemoLoggingAspect {


//firstly
    private Logger  mylogger = Logger.getLogger(getClass().getName());

    //setup pointcut declaration
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.controller.*.*(..) )")
//* type of method
//.controller name of package
//* name of class
//* name of method
//(..) argument in method
    private void forcontrollerpackage(){}

    //now i want doing this for all the package in this class

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.dao.*.*(..) )")
    private void fordaopackage(){}


    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.*(..) )")
    private void forservicepackage(){}

    //combine the pointcut
    @Pointcut("forcontrollerpackage()||fordaopackage()||forservicepackage()")
    private void ForAppFlow(){}

//please the ( ; ) not need to use

    //always inside Advice must place ""
    @Before("ForAppFlow()")
    public void before(JoinPoint joinPoint){

        String themethod =joinPoint.getSignature().toShortString();
        //my logger.info for show data
        mylogger.info("=======>>> in @Before calling method "+themethod);

        //display argument to the method

        //1) we have many argument
        Object [] args = joinPoint.getArgs();

        //ok now i want to display this arguments
        for(Object o : args){
            //here not print statement here i want to print my logger
            mylogger.info("=====>>"+o);
        }

    }


    @AfterReturning(
            //when create pointcut here we create  another form
            //the pointcut  without @
            pointcut = "ForAppFlow()",
            returning = "object" // here without ;
    )
public  void afterreturning (JoinPoint joinPoint ,Object object ){
        //here define object because not define any class or any type

        String themethod =joinPoint.getSignature().toShortString();
        mylogger.info("===============>>> in @AfterReturning from method "+themethod);

    //the different in after returning give te data by returning
        mylogger.info("==========> result "+object);
    }



}
