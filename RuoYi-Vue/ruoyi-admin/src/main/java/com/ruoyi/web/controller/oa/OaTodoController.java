package com.ruoyi.web.controller.oa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.oa.domain.OaIncomingDoc;
import com.ruoyi.oa.domain.OaOutgoingDoc;
import com.ruoyi.oa.service.IOaIncomingDocService;
import com.ruoyi.oa.service.IOaOutgoingDocService;

/**
 * OA��������
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/oa/todo")
public class OaTodoController extends BaseController
{
    @Autowired
    private IOaIncomingDocService oaIncomingDocService;

    @Autowired
    private IOaOutgoingDocService oaOutgoingDocService;

    /**
     * �����б�����ǰ��¼�û���
     */
    @PreAuthorize("@ss.hasAnyPermi('oa:incoming:list,oa:outgoing:list')")
    @GetMapping("/list")
    public AjaxResult list(
        @RequestParam(required = false) String todoType,
        @RequestParam(required = false) String status,
        @RequestParam(defaultValue = "1") Integer pageNum,
        @RequestParam(defaultValue = "10") Integer pageSize)
    {
        String username = SecurityUtils.getUsername();
        List<Map<String, Object>> todos = new ArrayList<>();

        OaIncomingDoc incomingQuery = new OaIncomingDoc();
        incomingQuery.setCreateBy(username);
        List<OaIncomingDoc> incomingDocs = oaIncomingDocService.selectOaIncomingDocList(incomingQuery);
        for (OaIncomingDoc doc : incomingDocs)
        {
            if ("0".equals(doc.getDocStatus()) || "1".equals(doc.getDocStatus()) || "2".equals(doc.getDocStatus()) || "3".equals(doc.getDocStatus()))
            {
                if (!isMatchType(todoType, "incoming") || !isMatchStatus(status, doc.getDocStatus()))
                {
                    continue;
                }
                Map<String, Object> item = new HashMap<>();
                item.put("todoType", "incoming");
                item.put("docId", doc.getDocId());
                item.put("docNum", doc.getDocNum());
                item.put("docTitle", doc.getDocTitle());
                item.put("status", doc.getDocStatus());
                item.put("statusLabel", getIncomingStatusLabel(doc.getDocStatus()));
                item.put("currentNode", doc.getCurrentNode());
                item.put("createTime", doc.getCreateTime());
                todos.add(item);
            }
        }

        OaOutgoingDoc outgoingQuery = new OaOutgoingDoc();
        outgoingQuery.setCreateBy(username);
        List<OaOutgoingDoc> outgoingDocs = oaOutgoingDocService.selectOaOutgoingDocList(outgoingQuery);
        for (OaOutgoingDoc doc : outgoingDocs)
        {
            if ("0".equals(doc.getDocStatus()) || "1".equals(doc.getDocStatus()) || "2".equals(doc.getDocStatus()))
            {
                if (!isMatchType(todoType, "outgoing") || !isMatchStatus(status, doc.getDocStatus()))
                {
                    continue;
                }
                Map<String, Object> item = new HashMap<>();
                item.put("todoType", "outgoing");
                item.put("docId", doc.getDocId());
                item.put("docNum", doc.getDocNum());
                item.put("docTitle", doc.getDocTitle());
                item.put("status", doc.getDocStatus());
                item.put("statusLabel", getOutgoingStatusLabel(doc.getDocStatus()));
                item.put("currentNode", doc.getCurrentNode());
                item.put("createTime", doc.getCreateTime());
                todos.add(item);
            }
        }

        int total = todos.size();
        int fromIndex = Math.max((pageNum - 1) * pageSize, 0);
        int toIndex = Math.min(fromIndex + pageSize, total);
        List<Map<String, Object>> pageRows = fromIndex >= total ? new ArrayList<>() : todos.subList(fromIndex, toIndex);

        Map<String, Object> data = new HashMap<>();
        data.put("rows", pageRows);
        data.put("total", total);
        return AjaxResult.success(data);
    }

    /**
     * ����ͳ��
     */
    @PreAuthorize("@ss.hasAnyPermi('oa:incoming:list,oa:outgoing:list')")
    @GetMapping("/summary")
    public AjaxResult summary()
    {
        String username = SecurityUtils.getUsername();
        int incomingCount = 0;
        int outgoingCount = 0;

        OaIncomingDoc incomingQuery = new OaIncomingDoc();
        incomingQuery.setCreateBy(username);
        List<OaIncomingDoc> incomingDocs = oaIncomingDocService.selectOaIncomingDocList(incomingQuery);
        for (OaIncomingDoc doc : incomingDocs)
        {
            if ("0".equals(doc.getDocStatus()) || "1".equals(doc.getDocStatus()) || "2".equals(doc.getDocStatus()) || "3".equals(doc.getDocStatus()))
            {
                incomingCount++;
            }
        }

        OaOutgoingDoc outgoingQuery = new OaOutgoingDoc();
        outgoingQuery.setCreateBy(username);
        List<OaOutgoingDoc> outgoingDocs = oaOutgoingDocService.selectOaOutgoingDocList(outgoingQuery);
        for (OaOutgoingDoc doc : outgoingDocs)
        {
            if ("0".equals(doc.getDocStatus()) || "1".equals(doc.getDocStatus()) || "2".equals(doc.getDocStatus()))
            {
                outgoingCount++;
            }
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("incomingTodoCount", incomingCount);
        summary.put("outgoingTodoCount", outgoingCount);
        summary.put("totalTodoCount", incomingCount + outgoingCount);
        return AjaxResult.success(summary);
    }

    private String getIncomingStatusLabel(String status)
    {
        if ("0".equals(status))
        {
            return "��ǩ��";
        }
        if ("1".equals(status))
        {
            return "���Ǽ�";
        }
        if ("2".equals(status))
        {
            return "������";
        }
        if ("3".equals(status))
        {
            return "������";
        }
        if ("4".equals(status))
        {
            return "�Ѱ��";
        }
        if ("5".equals(status))
        {
            return "�ѹ鵵";
        }
        return status;
    }

    private String getOutgoingStatusLabel(String status)
    {
        if ("0".equals(status))
        {
            return "�ݸ�";
        }
        if ("1".equals(status))
        {
            return "�����";
        }
        if ("2".equals(status))
        {
            return "�����";
        }
        if ("3".equals(status))
        {
            return "�ѷ���";
        }
        if ("4".equals(status))
        {
            return "�ѹ鵵";
        }
        return status;
    }

    private boolean isMatchType(String todoType, String value)
    {
        return todoType == null || todoType.isEmpty() || todoType.equals(value);
    }

    private boolean isMatchStatus(String status, String value)
    {
        return status == null || status.isEmpty() || status.equals(value);
    }
}
