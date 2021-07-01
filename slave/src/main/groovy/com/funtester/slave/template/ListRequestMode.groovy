package com.funtester.slave.template

import com.funtester.base.constaint.FixedThread
import com.funtester.base.constaint.ThreadBase
import com.funtester.httpclient.FunLibrary
import org.apache.http.client.methods.HttpRequestBase

class ListRequestMode<List> extends FixedThread {


    ListRequestMode(List<HttpRequestBase> res, int times) {
        super(res, times, true)
    }

    @Override
    protected void doing() throws Exception {
        //        FunLibrary.executeSimlple(res.get(index.getAndDecrement() % res.size()))
        FunLibrary.executeSimlple(random(f))
    }

    @Override
    ThreadBase clone() {
        return new ListRequestMode(f, limit);
    }
}
