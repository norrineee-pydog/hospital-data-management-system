package cn.iocoder.yudao.module.hospital.service.medicine;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.hospital.controller.admin.medicine.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.medicine.MedicineDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.hospital.dal.mysql.medicine.MedicineMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.*;

/**
 * 药品信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class MedicineServiceImpl implements MedicineService {

    @Resource
    private MedicineMapper medicineMapper;

    @Override
    public Integer createMedicine(MedicineSaveReqVO createReqVO) {
        // 插入
        MedicineDO medicine = BeanUtils.toBean(createReqVO, MedicineDO.class);
        medicineMapper.insert(medicine);

        // 返回
        return medicine.getId();
    }

    @Override
    public void updateMedicine(MedicineSaveReqVO updateReqVO) {
        // 校验存在
        validateMedicineExists(updateReqVO.getId());
        // 更新
        MedicineDO updateObj = BeanUtils.toBean(updateReqVO, MedicineDO.class);
        medicineMapper.updateById(updateObj);
    }

    @Override
    public void deleteMedicine(Integer id) {
        // 校验存在
        validateMedicineExists(id);
        // 删除
        medicineMapper.deleteById(id);
    }

    @Override
        public void deleteMedicineListByIds(List<Integer> ids) {
        // 删除
        medicineMapper.deleteByIds(ids);
        }


    private void validateMedicineExists(Integer id) {
        if (medicineMapper.selectById(id) == null) {
            throw exception(MEDICINE_NOT_EXISTS);
        }
    }

    @Override
    public MedicineDO getMedicine(Integer id) {
        return medicineMapper.selectById(id);
    }

    @Override
    public PageResult<MedicineDO> getMedicinePage(MedicinePageReqVO pageReqVO) {
        return medicineMapper.selectPage(pageReqVO);
    }

}