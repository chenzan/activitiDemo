package com.act.demo.controller;

import com.act.demo.service.IWorkFlowService;
import com.act.demo.support.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
@RequestMapping("/workflow")
public class WorkFlowController extends BaseController {
    @Resource
    IWorkFlowService workFlowService;

    /**
     * 查看完整流程图
     * @param deploymentId
     * @param resourceName
     */
    @RequestMapping("/processImg")
    public void processImg(String deploymentId, String resourceName) {
        InputStream is = workFlowService.getProcessDefResourceImg(deploymentId, resourceName);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            OutputStream out = response.getOutputStream();
            byte[] data = new byte[1024];
            while (is.read(data) != -1) {
                out.write(data);
            }
            is.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 查看完整流程图
     * @param taskId
     */
    @RequestMapping("/curProcessImg")
    public void curProcessImg(String taskId) {
        InputStream is = workFlowService.getProcessImgByTaskId(taskId);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            OutputStream out = response.getOutputStream();
            byte[] data = new byte[1024];
            while (is.read(data) != -1) {
                out.write(data);
            }
            is.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/deploymentDel")
    public String deploymentDel(String deploymentId) {
        workFlowService.deleteDeployment(deploymentId);
        return "redirect:/main/index";
    }

}
