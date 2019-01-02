package com.example.yudongcao.smartposdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.whty.smartpos.tysmartposapi.ITYSmartPosApi;
import com.whty.smartpos.tysmartposapi.cardreader.CardInfo;
import com.whty.smartpos.tysmartposapi.printer.PrinterConfig;
import com.whty.smartpos.tysmartposapi.printer.PrinterConstrants;

public class MainActivity extends AppCompatActivity {
    private ITYSmartPosApi smartPosApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // async function
        smartPosApi = ITYSmartPosApi.get(this);
        smartPosApi.initPrinter();
        smartPosApi.setReadCardConfig(true, true, true);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardReader:
                this.readCard();
                break;
            case R.id.printer:
                // must set print parameters
                Bundle bundle = new Bundle();
                bundle.putInt(PrinterConfig.FONT_SIZE, PrinterConstrants.FONT_SIZE_MIDDLE);
                bundle.putInt(PrinterConfig.ALIGN, PrinterConstrants.ALIGN_CENTER);
                bundle.putInt(PrinterConfig.CN_FONT, PrinterConstrants.KAITI);
                bundle.putInt(PrinterConfig.EN_FONT, PrinterConstrants.AVENAIR);
                smartPosApi.setPrintParameters(bundle);

                //print text
                smartPosApi.printText("Smart POS Demo");

                //print script
//                this.addScript();
//                smartPosApi.startPrint();
                break;
        }
    }


    private void addScript(){
        smartPosApi.appendScript("!hz n\n" +
                "!asc n\n" +
                "!gray 8\n" +
                "!NLFONT hz 0\n" +
                "!NLFONT asc 0\n" +
                "*image r 284*81 data:base64;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\n" +
                "*text l 商户名称:天喻测试商户\n" +
                "*text l 商户编号:123456789012345\n" +
                "*text l 终端编号:12345678\n" +
                "*text l 班次号:201707311234\n" +
                "*text l 发卡行:招商银行  收单行:中国银行\n" +
                "*text l 有效期:20190731\n" +
                "*text l 卡号:\n" +
                "!hz nl\n" +
                "!asc l\n" +
                "!gray 8\n" +
                "*text l 6225 75******5838/C\n" +
                "!hz nl\n" +
                "!asc l\n" +
                "!gray 8\n" +
                "*text l 交易类型:消费\n" +
                "!hz n\n" +
                "!asc n\n" +
                "!gray 8\n" +
                "*text l 凭证号:000001 授权码:415745\n" +
                "*text l 批次号:000045 参考号:458612356325\n" +
                "!hz n\n" +
                "!asc n\n" +
                "!gray 8\n" +
                "*text l 交易日期:2014/07/31 14:22:02\n" +
                "!hz n\n" +
                "!asc n\n" +
                "!gray 8\n" +
                "*text l 金额:\n" +
                "!hz nl\n" +
                "!asc l\n" +
                "!gray 8\n" +
                "*text l    RMB:0.01\n" +
                "!hz n\n" +
                "!asc n\n" +
                "!gray 8\n" +
                "*text l 备 注:\n" +
                "!hz sn\n" +
                "!asc sn\n" +
                "!gray 5\n" +
                "*text c ...............................................\n" +
                "!hz n\n" +
                "!asc n\n" +
                "!gray 8\n" +
                "*text l 持卡人签名:\n" +
                "!hz sn\n" +
                "*text l 小字体倍高:\n" +
                "!hz sl\n" +
                "*text l 小字体倍宽:\n" +
                "!hz nl\n" +
                "*text l 中字体倍高:\n" +
                "!hz hl\n" +
                "*text l 中字体倍宽:\n" +
                "!hz h\n" +
                "*text l 超大字体:\n" +
                "*text l    \n" +
                "*text l    \n" +
                "*text l    \n");
    }

    private void readCard() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CardInfo cardInfo = smartPosApi.readCard("1", "20171026101010", (byte) 0, (byte) 60);
                Log.e("card information : " , cardInfo.toString() + "\n");
                TextView tv1 = (TextView)findViewById(R.id.textView);
                tv1.setText(cardInfo.getCardNo());
            }
        });

    }


}
