package com.funtester.master.service.impl

import com.funtester.base.exception.FailException
import com.funtester.frame.SourceCode
import com.funtester.master.common.basedata.NodeData
import com.funtester.master.manaer.MasterManager
import com.funtester.master.service.IRunService
import com.funtester.slave.common.bean.run.*
import org.springframework.stereotype.Service

/**
 * 服务器执行类,以后可以Redis锁减少出错
 */
@Service
class RunService implements IRunService {

    @Override
    void runRequest(ManyRequest request) {
        def host = NodeData.getRunHost(1)
        MasterManager.runMany(host, request)
    }

    @Override
    int runRequest(HttpRequest request) {
        def num = request.getMark()
        def hosts = NodeData.getRunHost(num)
        def mark = SourceCode.getMark()
        request.setMark(mark)
        try {
            hosts.each {
                def re = MasterManager.runRequest(it, request)
                if (!re) FailException.fail()
                NodeData.addTask(it, mark)
            }
        } catch (FailException e) {
            hosts.each {f -> MasterManager.stop(f)}
            FailException.fail("多节点执行失败!")
        }
        mark
    }

    @Override
    int runRequests(HttpRequests requests) {
        def mark = SourceCode.getMark()
        def num = requests.getMark()
        def hosts = NodeData.getRunHost(num)
        requests.setMark(mark)
        try {
            hosts.each {
                def re = MasterManager.runRequests(it, requests)
                if (!re) FailException.fail()
                NodeData.addTask(it, mark)
            }
        } catch (FailException e) {
            hosts.each {f -> MasterManager.stop(f)}
            FailException.fail("多节点执行失败!")
        }
        mark
    }

    @Override
    int runMethod(LocalMethod method) {
        def mark = SourceCode.getMark()
        def num = method.getMark()
        def hosts = NodeData.getRunHost(num)
        method.setMark(mark)
        try {
            hosts.each {
                def re = MasterManager.runMethod(it, method)
                if (!re) FailException.fail()
                NodeData.addTask(it, mark)
            }
        } catch (FailException e) {
            hosts.each {f -> MasterManager.stop(f)}
            FailException.fail("多节点执行失败!")
        }
        mark
    }

    @Override
    int runScript(GroovyScript script) {
        def mark = SourceCode.getMark()
        def num = script.getMark()
        def hosts = NodeData.getRunHost(num)
        script.setMark(mark)
        try {
            hosts.each {
                def re = MasterManager.runRequest(it, script)
                if (!re) FailException.fail()
                NodeData.addTask(it, mark)
            }
        } catch (FailException e) {
            hosts.each {f -> MasterManager.stop(f)}
            FailException.fail("多节点执行失败!")
        }
        mark
    }

}
