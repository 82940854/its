package com.service;

import com.dao.KeywordsDao;
import com.entity.Keywords;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class KeywordsService {
    @Autowired
    private KeywordsDao keywordsDao;

    public Page<Keywords> findByPage(Keywords entity, int page, int size, String order, String sort) {
        PageRequest pageable;
        if (order !=null && sort!=null) {
            if (sort.toUpperCase().equals("DESC")) {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, new String[]{order}));
            } else {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, new String[]{order}));
            }
        } else {
            pageable = new PageRequest(page, size);
        }

        Specification<Keywords> spec = new Specification<Keywords>() {
            public Predicate toPredicate(Root<Keywords> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                ArrayList<Predicate> preList = new ArrayList();
                if (entity != null) {
                    Field[] declaredFields = entity.getClass().getDeclaredFields();

                    try {
                        Field idField = entity.getClass().getDeclaredField("id");
                        idField.setAccessible(true);
//                        Field deleteField = entity.getClass().getDeclaredField("isDelete");
//                        deleteField.setAccessible(true);
//
//                        try {
//                            Object value = deleteField.get(entity);
//                            Path<String> keyNamex = root.get("isDelete");
//                            Predicate px;
//                            if (value != null && (Integer)value == 1) {
//                                px = cb.equal(keyNamex, 1);
//                                preList.add(px);
//                            } else {
//                                px = cb.equal(keyNamex, 0);
//                                preList.add(px);
//                            }
//                        } catch (IllegalAccessException var15) {
//                            var15.printStackTrace();
//                        }

                        declaredFields = (Field[]) ArrayUtils.add(declaredFields, idField);
                    } catch (NoSuchFieldException var16) {
                        var16.printStackTrace();
                    }

                    Field[] var17 = declaredFields;
                    int var18 = declaredFields.length;

                    for(int var19 = 0; var19 < var18; ++var19) {
                        Field field = var17[var19];
                        field.setAccessible(true);
                        String key = field.getName();
                        Predicate p = null;

                        try {
                            Object paramVal = field.get(entity);
                            Path keyName;
                            if (paramVal instanceof String) {
                                keyName = root.get(key);
                                p = cb.equal(keyName, paramVal.toString());
                            } else if (paramVal instanceof Integer) {
                                keyName = root.get(key);
                                p = cb.equal(keyName, paramVal);
                            } else if (paramVal instanceof Date) {
                                keyName = root.get(key);
                                p = cb.equal(keyName, paramVal);
                            } else if (paramVal instanceof Date[]) {
                                keyName = root.get(key);
                                p = cb.between(keyName, ((Date[])((Date[])paramVal))[0], ((Date[])((Date[])paramVal))[1]);
                            }

                            if (p != null) {
                                preList.add(p);
                            }
                        } catch (IllegalAccessException var14) {
                            var14.printStackTrace();
                        }
                    }
                }

                return cb.and((Predicate[])preList.toArray(new Predicate[preList.size()]));
            }
        };
        return keywordsDao.findAll(spec, pageable);
    }
    @Transactional
    public void deleteByIds(String id) {
        if (StringUtils.hasText(id)) {
            String[] idArr = id.split(",");

            for(int i = 0; i < idArr.length; ++i) {
                this.deleteById(idArr[i]);
            }
        }

    }
    public void deleteById(String id) {
        this.keywordsDao.deleteById(id);
    }
    public Keywords get(String id) {
        if (!keywordsDao.findById(id).isPresent()) {
            return null;
        } else {
            return this.keywordsDao.findById(id).get();
        }
    }
    @Transactional
    public Keywords saveOrUpdate(Keywords entity) throws Exception {
        if (null == entity) {
            return null;
        } else {
            String id = entity.getId();
            if (!StringUtils.hasText(id)) {
                return this.keywordsDao.save(entity);
            } else {
                Keywords entityTemp = this.get(id);
                Field[] declaredFields = entity.getClass().getDeclaredFields();
                Field[] var6 = declaredFields;
                int var7 = declaredFields.length;

                int var8;
                Field superfield;
                Object value;
                for(var8 = 0; var8 < var7; ++var8) {
                    superfield = var6[var8];

                    try {
                        superfield.setAccessible(true);
                        value = superfield.get(entity);
                        if (value != null) {
                            superfield.set(entityTemp, value);
                        }
                    } catch (Exception var12) {
                        var12.printStackTrace();
                    }
                }
                return this.keywordsDao.save(entityTemp);
            }
        }
    }
    public List<Keywords> findByAll(){
        return keywordsDao.findAll();
    }
}
