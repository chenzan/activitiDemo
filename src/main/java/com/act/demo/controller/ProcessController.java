package com.act.demo.controller;

import com.act.demo.service.IProcessService;
import com.act.demo.support.BaseController;
import com.act.demo.support.ResponseResult;
import com.alibaba.fastjson.JSONObject;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/process")
public class ProcessController extends BaseController {
    @Autowired
    IProcessService processService;

    /**
     * 查看完整流程图
     *
     * @param deploymentId
     * @param resourceName
     */
    @RequestMapping("/processImg")
    public void processImg(String deploymentId, String resourceName) {
        InputStream is = processService.getProcessDefResourceImg(deploymentId, resourceName);
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
     * 查看当前流程图
     *
     * @param taskId
     */
    @RequestMapping("/curProcessImg")
    public void curProcessImg(String taskId) {
        InputStream is = processService.getProcessImgByTaskId(taskId);
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

    @ResponseBody
    @RequestMapping("/deploymentDel")
    public JSONObject deploymentDel(String deploymentId) {
        if (StringUtils.isEmpty(deploymentId)){
            return ResponseResult.error("部署id为空");
        }
        processService.deleteDeployment(deploymentId);
        return ResponseResult.success();
    }

    /**
     * 获取流程定义信息
     *
     * @return
     */
    @RequestMapping("/processDef")
    public String processDef(org.springframework.ui.Model model) {
        List<ProcessDefinition> definitionProcess = processService.getDefinitionProcess();
        model.addAttribute("definitionProcess", definitionProcess);
        return "process/processDef";
    }

    @RequestMapping("/processDeployment")
    public String processDeployments(Map map) {
        List<Deployment> deploymentProcess = processService.getDeploymentProcess();
        map.put("deploymentProcess", deploymentProcess);
        return "process/processDeployment";
    }

}
