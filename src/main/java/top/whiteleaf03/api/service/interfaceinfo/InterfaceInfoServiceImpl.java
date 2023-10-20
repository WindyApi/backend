package top.whiteleaf03.api.service.interfaceinfo;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.whiteleaf03.api.modal.document.es.InterfaceInfoESDocument;
import top.whiteleaf03.api.repository.es.InterfaceInfoESRepository;
import top.whiteleaf03.api.mapper.InterfaceInfoMapper;
import top.whiteleaf03.api.mapper.UserMapper;
import top.whiteleaf03.api.modal.document.InterfaceInfoDocument;
import top.whiteleaf03.api.modal.dto.*;
import top.whiteleaf03.api.modal.entity.InterfaceInfo;
import top.whiteleaf03.api.modal.vo.PageSizeVO;
import top.whiteleaf03.api.modal.entity.User;
import top.whiteleaf03.api.modal.vo.InterfaceDocVO;
import top.whiteleaf03.api.modal.vo.InterfaceInfoVO;
import top.whiteleaf03.api.modal.vo.OnlineInterfaceInfoVO;
import top.whiteleaf03.api.repository.mongo.InterfaceInfoMongoRepository;
import top.whiteleaf03.api.util.ResponseResult;

import java.util.*;

/**
 * @author WhiteLeaf03
 */
@Slf4j
@Service
public class InterfaceInfoServiceImpl implements InterfaceInfoService {
    private final InterfaceInfoMapper interfaceInfoMapper;
    private final UserMapper userMapper;
    private final InterfaceInfoMongoRepository interfaceInfoMongoRepository;
    private final InterfaceInfoESRepository interfaceInfoESRepositoryRepository;

    @Autowired
    public InterfaceInfoServiceImpl(InterfaceInfoMapper interfaceInfoMapper, UserMapper userMapper, InterfaceInfoMongoRepository interfaceInfoMongoRepository, InterfaceInfoESRepository interfaceInfoESRepositoryRepository) {
        this.interfaceInfoMapper = interfaceInfoMapper;
        this.userMapper = userMapper;
        this.interfaceInfoMongoRepository = interfaceInfoMongoRepository;
        this.interfaceInfoESRepositoryRepository = interfaceInfoESRepositoryRepository;
    }

    /**
     * 新增接口
     *
     * @param newInterfaceDTO 新增接口的信息
     * @return 返回结果
     */
    @Override
    public ResponseResult insert(NewInterfaceDTO newInterfaceDTO) {
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute("UserInfo", RequestAttributes.SCOPE_REQUEST);
        newInterfaceDTO.setUserId(user.getId());
        interfaceInfoMapper.insert(newInterfaceDTO);
        InterfaceInfoDocument interfaceInfoDocument = new InterfaceInfoDocument(newInterfaceDTO);
        interfaceInfoMongoRepository.save(interfaceInfoDocument);
        return ResponseResult.success();
    }

    /**
     * 修改接口状态
     * 0 下线
     * 1 上线
     *
     * @param updateInterfaceStatusDTO 修改接口的信息
     * @return 返回结果
     */
    @Override
    public ResponseResult updateStatusById(UpdateInterfaceStatusDTO updateInterfaceStatusDTO) {
        interfaceInfoMapper.updateStatusById(updateInterfaceStatusDTO);
        return ResponseResult.success();
    }

    /**
     * 上下线接口
     *
     * @param updateInterfaceStatusDTO 接口信息
     * @return 返回结果
     */
    @Override
    public ResponseResult setStatus(UpdateInterfaceStatusDTO updateInterfaceStatusDTO) {
        interfaceInfoMapper.updateStatusById(updateInterfaceStatusDTO);
        return ResponseResult.success();
    }

    /**
     * 获取分页总数
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult queryAliveInterfacePageSize() {
        Long total = interfaceInfoMapper.selectCountByStatusAndIsDelete();
        return ResponseResult.success(new PageSizeVO(total));
    }

    /**
     * 查询活跃接口信息
     *
     * @param pageNumDTO 分页信息
     * @return 返回结果
     */
    @Override
    public ResponseResult queryAliveInterfaceByPage(PageNumDTO pageNumDTO) {
        List<OnlineInterfaceInfoVO> onlineInterfaceInfoVOList = interfaceInfoMapper.selectIdAndNameAndDescribeByPageNumAndStatusAndIsDelete(pageNumDTO);
        return ResponseResult.success(onlineInterfaceInfoVOList);
    }

    /**
     * 获取接口文档
     *
     * @param interfaceIdDTO 接口id
     * @return 返回结果
     */
    @Override
    public ResponseResult queryInterfaceDocById(InterfaceIdDTO interfaceIdDTO) {
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectNameAndDescribeAndMethodAndUrlAndStatusAndCreateTimeAndUpdateTimeByIdAndIsDelete(interfaceIdDTO);
        InterfaceInfoDocument interfaceInfoDocument = interfaceInfoMongoRepository.findByInterfaceInfoId(interfaceIdDTO.getId()).get(0);
        InterfaceDocVO interfaceDocVO = new InterfaceDocVO(interfaceInfo, interfaceInfoDocument);
        return ResponseResult.success(interfaceDocVO);
    }

    /**
     * 获取所有接口分页总数
     *
     * @return 返回结果
     */
    @Override
    public ResponseResult queryAllInterfacePageSize() {
        Long total = interfaceInfoMapper.selectCountByIsDelete();
        return ResponseResult.success(new PageSizeVO(total));
    }

    /**
     * 根据页号查询接口信息
     *
     * @param pageNumDTO 页号
     * @return 返回接口信息
     */
    @Override
    public ResponseResult queryAllInterfaceByPage(PageNumDTO pageNumDTO) {
        List<InterfaceInfo> interfaceInfos = interfaceInfoMapper.selectIdAndNameAndDescribeAndMethodAndUrlAndStatusAndUserIdAndCreateTimeAndUpdateTimeByPageNumAndIsDelete(pageNumDTO);
        List<InterfaceInfoVO> interfaceInfoVOs = new ArrayList<>();
        for (InterfaceInfo interfaceInfo : interfaceInfos) {
            InterfaceInfoDocument interfaceInfoDocument = interfaceInfoMongoRepository.findByInterfaceInfoId(interfaceInfo.getId()).get(0);
            interfaceInfoVOs.add(new InterfaceInfoVO(interfaceInfo, userMapper.selectNicknameById(interfaceInfo.getUserId()), interfaceInfoDocument));
        }
        return ResponseResult.success(interfaceInfoVOs);
    }

    /**
     * 修改接口信息
     *
     * @param updateInterfaceDTO 要修改的接口信息
     * @return 返回结果
     */
    @Override
    public ResponseResult updateInterfaceById(UpdateInterfaceDTO updateInterfaceDTO) {
        interfaceInfoMapper.updateNameOrDescribeOrMethodOrUrlById(updateInterfaceDTO);
        Map<String, Object> data = new HashMap<>();
        data.put("params", updateInterfaceDTO.getParams());
        data.put("requestHeader", updateInterfaceDTO.getRequestHeader());
        data.put("responseHeader", updateInterfaceDTO.getResponseHeader());
        interfaceInfoMongoRepository.update(updateInterfaceDTO.getId(), updateInterfaceDTO.getRequestHeader(), updateInterfaceDTO.getResponseHeader(), updateInterfaceDTO.getParams());
        return ResponseResult.success();
    }

    /**
     * 搜索接口
     *
     * @param searchDTO 包含关键词
     * @return 返回结果
     */
    @Override
    public ResponseResult getInterfaceInfoByKeyword(SearchDTO searchDTO) {
        if (StrUtil.isBlank(searchDTO.getKeyword())) {
            return ResponseResult.error("参数不允许为空");
        }
        List<InterfaceInfoESDocument> interfaceInfoESDocuments = interfaceInfoESRepositoryRepository.queryInterfaceInfoESDocumentByKeywordInNameOrDescribe(searchDTO.getKeyword());
        return ResponseResult.success(interfaceInfoESDocuments);
    }
}
