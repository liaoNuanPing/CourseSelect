package pojo;

import java.util.ArrayList;
import java.util.List;

public class PerClassCourseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PerClassCourseExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNull() {
            addCriterion("course_id is null");
            return (Criteria) this;
        }

        public Criteria andCourseIdIsNotNull() {
            addCriterion("course_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourseIdEqualTo(Integer value) {
            addCriterion("course_id =", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotEqualTo(Integer value) {
            addCriterion("course_id <>", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThan(Integer value) {
            addCriterion("course_id >", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("course_id >=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThan(Integer value) {
            addCriterion("course_id <", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdLessThanOrEqualTo(Integer value) {
            addCriterion("course_id <=", value, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdIn(List<Integer> values) {
            addCriterion("course_id in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotIn(List<Integer> values) {
            addCriterion("course_id not in", values, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdBetween(Integer value1, Integer value2) {
            addCriterion("course_id between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andCourseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("course_id not between", value1, value2, "courseId");
            return (Criteria) this;
        }

        public Criteria andGradeIsNull() {
            addCriterion("grade is null");
            return (Criteria) this;
        }

        public Criteria andGradeIsNotNull() {
            addCriterion("grade is not null");
            return (Criteria) this;
        }

        public Criteria andGradeEqualTo(String value) {
            addCriterion("grade =", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotEqualTo(String value) {
            addCriterion("grade <>", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeGreaterThan(String value) {
            addCriterion("grade >", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeGreaterThanOrEqualTo(String value) {
            addCriterion("grade >=", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLessThan(String value) {
            addCriterion("grade <", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLessThanOrEqualTo(String value) {
            addCriterion("grade <=", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeLike(String value) {
            addCriterion("grade like", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotLike(String value) {
            addCriterion("grade not like", value, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeIn(List<String> values) {
            addCriterion("grade in", values, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotIn(List<String> values) {
            addCriterion("grade not in", values, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeBetween(String value1, String value2) {
            addCriterion("grade between", value1, value2, "grade");
            return (Criteria) this;
        }

        public Criteria andGradeNotBetween(String value1, String value2) {
            addCriterion("grade not between", value1, value2, "grade");
            return (Criteria) this;
        }

        public Criteria andTermIsNull() {
            addCriterion("term is null");
            return (Criteria) this;
        }

        public Criteria andTermIsNotNull() {
            addCriterion("term is not null");
            return (Criteria) this;
        }

        public Criteria andTermEqualTo(String value) {
            addCriterion("term =", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotEqualTo(String value) {
            addCriterion("term <>", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermGreaterThan(String value) {
            addCriterion("term >", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermGreaterThanOrEqualTo(String value) {
            addCriterion("term >=", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermLessThan(String value) {
            addCriterion("term <", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermLessThanOrEqualTo(String value) {
            addCriterion("term <=", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermLike(String value) {
            addCriterion("term like", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotLike(String value) {
            addCriterion("term not like", value, "term");
            return (Criteria) this;
        }

        public Criteria andTermIn(List<String> values) {
            addCriterion("term in", values, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotIn(List<String> values) {
            addCriterion("term not in", values, "term");
            return (Criteria) this;
        }

        public Criteria andTermBetween(String value1, String value2) {
            addCriterion("term between", value1, value2, "term");
            return (Criteria) this;
        }

        public Criteria andTermNotBetween(String value1, String value2) {
            addCriterion("term not between", value1, value2, "term");
            return (Criteria) this;
        }

        public Criteria andToClassIsNull() {
            addCriterion("to_class is null");
            return (Criteria) this;
        }

        public Criteria andToClassIsNotNull() {
            addCriterion("to_class is not null");
            return (Criteria) this;
        }

        public Criteria andToClassEqualTo(String value) {
            addCriterion("to_class =", value, "toClass");
            return (Criteria) this;
        }

        public Criteria andToClassNotEqualTo(String value) {
            addCriterion("to_class <>", value, "toClass");
            return (Criteria) this;
        }

        public Criteria andToClassGreaterThan(String value) {
            addCriterion("to_class >", value, "toClass");
            return (Criteria) this;
        }

        public Criteria andToClassGreaterThanOrEqualTo(String value) {
            addCriterion("to_class >=", value, "toClass");
            return (Criteria) this;
        }

        public Criteria andToClassLessThan(String value) {
            addCriterion("to_class <", value, "toClass");
            return (Criteria) this;
        }

        public Criteria andToClassLessThanOrEqualTo(String value) {
            addCriterion("to_class <=", value, "toClass");
            return (Criteria) this;
        }

        public Criteria andToClassLike(String value) {
            addCriterion("to_class like", value, "toClass");
            return (Criteria) this;
        }

        public Criteria andToClassNotLike(String value) {
            addCriterion("to_class not like", value, "toClass");
            return (Criteria) this;
        }

        public Criteria andToClassIn(List<String> values) {
            addCriterion("to_class in", values, "toClass");
            return (Criteria) this;
        }

        public Criteria andToClassNotIn(List<String> values) {
            addCriterion("to_class not in", values, "toClass");
            return (Criteria) this;
        }

        public Criteria andToClassBetween(String value1, String value2) {
            addCriterion("to_class between", value1, value2, "toClass");
            return (Criteria) this;
        }

        public Criteria andToClassNotBetween(String value1, String value2) {
            addCriterion("to_class not between", value1, value2, "toClass");
            return (Criteria) this;
        }

        public Criteria andTotalNeedStuAmountIsNull() {
            addCriterion("total_need_stu_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalNeedStuAmountIsNotNull() {
            addCriterion("total_need_stu_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalNeedStuAmountEqualTo(Integer value) {
            addCriterion("total_need_stu_amount =", value, "totalNeedStuAmount");
            return (Criteria) this;
        }

        public Criteria andTotalNeedStuAmountNotEqualTo(Integer value) {
            addCriterion("total_need_stu_amount <>", value, "totalNeedStuAmount");
            return (Criteria) this;
        }

        public Criteria andTotalNeedStuAmountGreaterThan(Integer value) {
            addCriterion("total_need_stu_amount >", value, "totalNeedStuAmount");
            return (Criteria) this;
        }

        public Criteria andTotalNeedStuAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_need_stu_amount >=", value, "totalNeedStuAmount");
            return (Criteria) this;
        }

        public Criteria andTotalNeedStuAmountLessThan(Integer value) {
            addCriterion("total_need_stu_amount <", value, "totalNeedStuAmount");
            return (Criteria) this;
        }

        public Criteria andTotalNeedStuAmountLessThanOrEqualTo(Integer value) {
            addCriterion("total_need_stu_amount <=", value, "totalNeedStuAmount");
            return (Criteria) this;
        }

        public Criteria andTotalNeedStuAmountIn(List<Integer> values) {
            addCriterion("total_need_stu_amount in", values, "totalNeedStuAmount");
            return (Criteria) this;
        }

        public Criteria andTotalNeedStuAmountNotIn(List<Integer> values) {
            addCriterion("total_need_stu_amount not in", values, "totalNeedStuAmount");
            return (Criteria) this;
        }

        public Criteria andTotalNeedStuAmountBetween(Integer value1, Integer value2) {
            addCriterion("total_need_stu_amount between", value1, value2, "totalNeedStuAmount");
            return (Criteria) this;
        }

        public Criteria andTotalNeedStuAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("total_need_stu_amount not between", value1, value2, "totalNeedStuAmount");
            return (Criteria) this;
        }

        public Criteria andHaveStuAmountIsNull() {
            addCriterion("have_stu_amount is null");
            return (Criteria) this;
        }

        public Criteria andHaveStuAmountIsNotNull() {
            addCriterion("have_stu_amount is not null");
            return (Criteria) this;
        }

        public Criteria andHaveStuAmountEqualTo(Integer value) {
            addCriterion("have_stu_amount =", value, "haveStuAmount");
            return (Criteria) this;
        }

        public Criteria andHaveStuAmountNotEqualTo(Integer value) {
            addCriterion("have_stu_amount <>", value, "haveStuAmount");
            return (Criteria) this;
        }

        public Criteria andHaveStuAmountGreaterThan(Integer value) {
            addCriterion("have_stu_amount >", value, "haveStuAmount");
            return (Criteria) this;
        }

        public Criteria andHaveStuAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("have_stu_amount >=", value, "haveStuAmount");
            return (Criteria) this;
        }

        public Criteria andHaveStuAmountLessThan(Integer value) {
            addCriterion("have_stu_amount <", value, "haveStuAmount");
            return (Criteria) this;
        }

        public Criteria andHaveStuAmountLessThanOrEqualTo(Integer value) {
            addCriterion("have_stu_amount <=", value, "haveStuAmount");
            return (Criteria) this;
        }

        public Criteria andHaveStuAmountIn(List<Integer> values) {
            addCriterion("have_stu_amount in", values, "haveStuAmount");
            return (Criteria) this;
        }

        public Criteria andHaveStuAmountNotIn(List<Integer> values) {
            addCriterion("have_stu_amount not in", values, "haveStuAmount");
            return (Criteria) this;
        }

        public Criteria andHaveStuAmountBetween(Integer value1, Integer value2) {
            addCriterion("have_stu_amount between", value1, value2, "haveStuAmount");
            return (Criteria) this;
        }

        public Criteria andHaveStuAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("have_stu_amount not between", value1, value2, "haveStuAmount");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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