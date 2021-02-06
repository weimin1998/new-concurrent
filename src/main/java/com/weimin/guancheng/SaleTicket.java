package com.weimin.guancheng;

import com.weimin.util.MyUtil;

/**
 * ticket
 */
public class SaleTicket {
    public static void main(String[] args) {
        TicketWindow ticketWindow = new TicketWindow(100);

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                ticketWindow.sell();
            }).start();
        }
    }
}


class TicketWindow{
    private int num;

    public TicketWindow(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public  void sell(){
        if(num>0){
            MyUtil.print(num);
            num -=1;
        }else {
            MyUtil.print("没了。。");
        }
    }



}
