package ${targetPackage}.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.framework.model.example.BaseExample;

public class ${domainObjectName}Example extends BaseExample {
    
    protected List<Criteria> oredCriteria;
    
    public ${domainObjectName}Example() {
        oredCriteria = new ArrayList<Criteria>();
    }
	
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }
 
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        
        groupByClause = null;
        column = null;
        offset = null;
        pageSize = null;
        sortKey = null;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }
<#list columns as column>
        public Criteria and${column.methodPropertyName}IsNull() {
            addCriterion("${column.name} is null");
            return (Criteria) this;
        }

        public Criteria and${column.methodPropertyName}IsNotNull() {
            addCriterion("${column.name} is not null");
            return (Criteria) this;
        }

        public Criteria and${column.methodPropertyName}EqualTo(${column.className} value) {
            addCriterion("${column.name} =", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.methodPropertyName}NotEqualTo(${column.className} value) {
            addCriterion("${column.name} <>", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.methodPropertyName}GreaterThan(${column.className} value) {
            addCriterion("${column.name} >", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.methodPropertyName}GreaterThanOrEqualTo(${column.className} value) {
            addCriterion("${column.name} >=", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.methodPropertyName}LessThan(${column.className} value) {
            addCriterion("${column.name} <", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.methodPropertyName}LessThanOrEqualTo(${column.className} value) {
            addCriterion("${column.name} <=", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.methodPropertyName}In(List<${column.className}> values) {
            addCriterion("${column.name} in", values, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.methodPropertyName}NotIn(List<${column.className}> values) {
            addCriterion("${column.name} not in", values, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.methodPropertyName}Between(${column.className} value1, ${column.className} value2) {
            addCriterion("${column.name} between", value1, value2, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.methodPropertyName}NotBetween(${column.className} value1, ${column.className} value2) {
            addCriterion("${column.name} not between", value1, value2, "${column.name}");
            return (Criteria) this;
        }
        
        <#if column.className == "String">
        public Criteria and${column.methodPropertyName}Like(${column.className} value) {
            addCriterion("${column.name} like", value, "${column.name}");
            return (Criteria) this;
        }

        public Criteria and${column.methodPropertyName}NotLike(${column.className} value) {
            addCriterion("${column.name} not like", value, "${column.name}");
            return (Criteria) this;
        }
        </#if>
   </#list>     
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ecc_orderinfo
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ecc_orderinfo
     *
     * @mbggenerated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}