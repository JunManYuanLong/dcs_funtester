package com.funtester.master.service.impl

import com.funtester.base.exception.FailException
import com.funtester.frame.SourceCode
import com.funtester.master.common.basedata.NodeData
import com.funtester.master.manaer.MasterManager
import com.funtester.master.service.IRunService
import com.funtester.slave.common.bean.run.GroovyScript
import com.funtester.slave.common.bean.run.HttpRequest
import com.funtester.slave.common.bean.run.HttpRequests
import com.funtester.slave.common.bean.run.LocalMethod
import org.springframework.stereotype.Service

/**
 * 服务器执行类,以后可以Redis锁减少出错
 */
@Service
class RunService implements IRunService {

    @Override
    int runRequest(HttpRequest request) {
        def num = request.getMark()
        def hosts = NodeData.getRunHost(num)
        def mark = SourceCode.getMark()
        try {
            hosts.each {
                request.setMark(mark)
                def re = MasterManager.runRequest(it, request)
                if (!re) FailException.fail()
                NodeData.addTask(it, mark)
            }
        } catch (FailException e) {
            hosts.each { f -> MasterManager.stop(f) }
            FailException.fail("多节点执行失败!")
        }
        mark
    }

    @Override
    int runRequests(HttpRequests requests) {
        def mark = SourceCode.getMark()
        def num = requests.getMark()
        def hosts = NodeData.getRunHost(num)
        try {
            hosts.each {
                requests.setMark(mark)
                def re = MasterManager.runRequests(it, requests)
                if (!re) FailException.fail()
                NodeData.addTask(it, mark)
            }
        } catch (FailException e) {
            hosts.each { f -> MasterManager.stop(f) }
            FailException.fail("多节点执行失败!")
        }
        mark
    }

    @Override
    int runMethod(LocalMethod method) {
        def mark = SourceCode.getMark()
        def num = method.getMark()
        def hosts = NodeData.getRunHost(num)
        try {
            hosts.each {
                method.setMark(mark)
                def re = MasterManager.runMethod(it, method)
                if (!re) FailException.fail()
                NodeData.addTask(it, mark)
            }
        } catch (FailException e) {
            hosts.each { f -> MasterManager.stop(f) }
            FailException.fail("多节点执行失败!")
        }
        mark
    }

    @Override
    int runScript(GroovyScript script) {
        def mark = SourceCode.getMark()
        def num = script.getMark()
        def hosts = NodeData.getRunHost(num)
        try {
            hosts.each {
                script.setMark(mark)
                def re = MasterManager.runRequest(it, script)
                if (!re) FailException.fail()
                NodeData.addTask(it, mark)
            }
        } catch (FailException e) {
            hosts.each { f -> MasterManager.stop(f) }
            FailException.fail("多节点执行失败!")
        }
        mark
    }

}
