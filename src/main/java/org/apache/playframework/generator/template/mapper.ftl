<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${daoTargetPackage}.${domainObjectName}Dao">
  
  <!-- <cache type="org.mybatis.caches.ehcache.EhcacheCache" flushInterval="6000000" size="1024"  readOnly="true"/>  -->
  
  <resultMap id="BaseResultMap" type="${modelTargetPackage}.${domainObjectName}">
  <#list columns as column>
    <#if column.name == pkColumn.name>
     <id column="${column.name}"  property="${column.propertyName }" />
    </#if>
  </#list> 
  <#list columns as column>
    <#if column.name != pkColumn.name>
     <result column="${column.name}" property="${column.propertyName}" />
    </#if>
  </#list> 
  </resultMap>
  
  <sql id="Example_Where_Clause">
      <trim prefix="" prefixOverrides="and" suffix="">
	      <foreach collection="oredCriteria" item="criteria" separator="or">
	        <if test="criteria.valid">
	            <foreach collection="criteria.criteria" item="criterion">
	              <choose>
	                <when test="criterion.noValue">
	                  and ${r'${criterion.condition}'} 
	                </when>
	                <when test="criterion.singleValue">
	                  and ${r'${criterion.condition}'} ${r'#{criterion.value}'}
	                </when>
	                <when test="criterion.betweenValue">
	                  and ${r'${criterion.condition}'} ${r'#{criterion.value}'} and ${r'#{criterion.secondValue}'}
	                </when>
	                <when test="criterion.listValue">
	                  and ${r'${criterion.condition}'}
	                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
	                    ${r'#{listItem}'}
	                  </foreach>
	                </when>
	              </choose>
	            </foreach>
	        </if>
	      </foreach>
	      <include refid="IsDelete_Where_Clause" />
      </trim>
  </sql>
  
  <sql id="Select_Example_Where_Clause">
      <trim prefix="" prefixOverrides="and" suffix="">
          <include refid="selectStartPageId" />
	      <foreach collection="oredCriteria" item="criteria" separator="or">
	        <if test="criteria.valid">
	            <foreach collection="criteria.criteria" item="criterion">
	              <choose>
	                <when test="criterion.noValue">
	                  and ${r'${criterion.condition}'} 
	                </when>
	                <when test="criterion.singleValue">
	                  and ${r'${criterion.condition}'} ${r'#{criterion.value}'}
	                </when>
	                <when test="criterion.betweenValue">
	                  and ${r'${criterion.condition}'} ${r'#{criterion.value}'} and ${r'#{criterion.secondValue}'}
	                </when>
	                <when test="criterion.listValue">
	                  and ${r'${criterion.condition}'}
	                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
	                    ${r'#{listItem}'}
	                  </foreach>
	                </when>
	              </choose>
	            </foreach>
	        </if>
	      </foreach>
	      <include refid="IsDelete_Where_Clause" />
      </trim>
  </sql>
  
  <sql id="Update_By_Example_Where_Clause">
      <trim prefix="" prefixOverrides="and" suffix="">
	      <foreach collection="example.oredCriteria" item="criteria" separator="or">
	        <if test="criteria.valid">
	            <foreach collection="criteria.criteria" item="criterion">
	              <choose>
	                <when test="criterion.noValue">
	                  and ${r'${criterion.condition}'}
	                </when>
	                <when test="criterion.singleValue">
	                  and ${r'${criterion.condition}'} ${r'#{criterion.value}'}
	                </when>
	                <when test="criterion.betweenValue">
	                  and ${r'${criterion.condition}'} ${r'#{criterion.value}'} and ${r'#{criterion.secondValue}'}
	                </when>
	                <when test="criterion.listValue">
	                  and ${r'${criterion.condition}'}
	                  <foreach close=")" collection="criterion.value" item="listItem" open="(" separator=",">
	                    ${r'#{listItem}'}
	                  </foreach>
	                </when>
	              </choose>
	            </foreach>
	        </if>
	      </foreach>
	      <include refid="IsDelete_Where_Clause" />
      </trim>
  </sql>
  
  <sql id="Base_Column_List">
     ${columnList }
  </sql>
  
  <sql id="selectStartPageId">
     <if test="offset != null">
         and id 
         <choose>
			<when test="sortKey == 'desc'">&lt;=</when>
			<otherwise>
				&gt;=
			</otherwise>
		</choose>
          
         (select
		    <if test="distinct">
		      distinct
		    </if>
		    id
		    from ${tableName}
		    <if test="_parameter != null">
		      <where><include refid="Example_Where_Clause" /> </where>
		    </if>
		    <if test="groupByClause != null">
		      group by ${r'${groupByClause}'}
		    </if>
		    <if test="orderByClause != null">
		      order by ${r'${orderByClause}'}
		    </if>  limit ${r'${offset}'}, 1)
     </if>
  </sql>
  
  <sql id="IsDelete_Where_Clause">
      <#list columns as column>
	    <#if column.propertyName == 'isDelete'>
	        and is_delete = 'N'
	    </#if>
	  </#list>
  </sql>
  
  <select id="selectByExample" parameterType="${modelTargetPackage}.example.${domainObjectName}Example" resultMap="BaseResultMap">
    select
    <if test="distinct">
      distinct
    </if>
    <choose>
		<when test="column != null">${r'${column}'}</when>
		<otherwise>
			<include refid="Base_Column_List" />
		</otherwise>
	</choose>
    from ${tableName}
    
    <choose>
		<when test="pagerKey == 'id'">
             <if test="_parameter != null">
		        <where>
			      <include refid="Select_Example_Where_Clause" /> 
		        </where>
		     </if>
		     <if test="groupByClause != null">
		       group by ${r'${groupByClause}'}
		     </if>
		     <if test="orderByClause != null">
		       order by ${r'${orderByClause}'}
		     </if>
		     <if test="pageSize != null">
		        limit ${r'${pageSize}'}
		     </if>
        </when>
		<otherwise>
			<if test="_parameter != null">
		        <where>
			      <include refid="Example_Where_Clause" /> 
		        </where>
		    </if>
		    <if test="groupByClause != null">
		      group by ${r'${groupByClause}'}
		    </if>
		    <if test="orderByClause != null">
		      order by ${r'${orderByClause}'}
		    </if>
		    <if test="pageSize != null">
		       limit ${r'${offset}'},${r'${pageSize}'}
		    </if>
		</otherwise>
	</choose>
		
 
  </select>
  
  <select id="selectById" parameterType="${pkColumn.className }" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${tableName}
    where id = ${r'#{id}'}
    <include refid="IsDelete_Where_Clause" />
  </select>
  
  <delete id="deleteById" parameterType="${pkColumn.className }">
    delete from ${tableName}
    where id = ${r'#{id}'} 
    <include refid="IsDelete_Where_Clause" />
  </delete>
  
  <delete id="deleteByExample" parameterType="${modelTargetPackage}.example.${domainObjectName}Example">
    delete from ${tableName}
    <if test="_parameter != null">
      <where>
         <include refid="Example_Where_Clause" /> 
         <include refid="IsDelete_Where_Clause" />
      </where>
    </if>
  </delete>
   
  <insert id="insert" parameterType="${modelTargetPackage}.${domainObjectName}" useGeneratedKeys="true" keyProperty="${pkColumn.propertyName}">
    insert into ${tableName}
    <trim prefix="(" suffix=")" suffixOverrides=",">
      <#list columns as column>
	  <if test="${column.propertyName} != null">
        ${column.name},
      </if>
	  </#list>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
      <#list columns as column>
      <if test="${column.propertyName} != null">
        ${r'#{'}${column.propertyName}${r'}'},
      </if>
	  </#list>
     </trim>
  </insert>
  
  <select id="countByExample" parameterType="${modelTargetPackage}.example.${domainObjectName}Example" resultType="java.lang.Integer">
    <if test="groupByClause != null">
     select count(*) from (
    </if>
       select
       <choose>
			<when test="distinct and column != null">count(distinct  ${r'${column}'}) </when>
			<otherwise>
				count(*)
			</otherwise>
	   </choose>
       from ${tableName}
    <if test="_parameter != null">
      <where>
         <include refid="Example_Where_Clause" /> 
      </where>
    </if>
    <if test="groupByClause != null">
      group by ${r'${groupByClause}'}
    ) as a
    </if>
  </select>
  
  <select id="selectByModel" resultMap="BaseResultMap" parameterType="${modelTargetPackage}.${domainObjectName}">
		select
		<include refid="Base_Column_List" />
		from ${tableName}
		<where>
			<#list columns as column>
		      <if test="${column.propertyName} != null"> 
				 and ${column.name} =  ${r'#{'}${column.propertyName}${r'}'}
			  </if>
			</#list>
			<include refid="IsDelete_Where_Clause" />
		</where>
	</select>
	
   <select id="sumList" parameterType="${modelTargetPackage}.example.${domainObjectName}Example" resultMap="BaseResultMap">
	    select
	    <if test="distinct">
	      distinct
	    </if>
	    <choose>
			<when test="column != null">${r'${column}'}</when>
			<otherwise>
			    <trim prefix="values (" suffix=")" suffixOverrides=",">
				<#list columns as column>
				    <#if column.className == "Long" || column.className == "Integer" || column.className == "BigDecimal">
				        sum(${column.name}) as ${column.propertyName},
				    </#if>
				</#list>
				</trim>
			</otherwise>
		</choose>
	    from ${tableName}
	    <if test="_parameter != null">
	       <where>
		      <include refid="Example_Where_Clause" /> 
		      <include refid="IsDelete_Where_Clause" />
	       </where>
	    </if>
	    <if test="groupByClause != null">
	      group by ${r'${groupByClause}'}
	    </if>
	    <if test="orderByClause != null">
	      order by ${r'${orderByClause}'}
	    </if>
  </select>
  
  <update id="updateByExample" parameterType="map">
    update ${tableName}
    <set>
      <#list columns as column>
	    <#if column.name != pkColumn.name>
      <if test="record.${column.propertyName} != null">
        ${column.name} = ${r'#{record.'}${column.propertyName}${r'}'},
      </if>
	    </#if>
	  </#list> 
    </set>
    <if test="_parameter != null">
       <where>
	      <include refid="Update_By_Example_Where_Clause" />
	      <include refid="IsDelete_Where_Clause" />
	   </where>
    </if>
  </update>
  
  <update id="updateById" parameterType="${modelTargetPackage}.${domainObjectName}">
    update ${tableName}
    <set>
      <#list columns as column>
	    <#if column.name != pkColumn.name>
	  <if test="${column.propertyName} != null">
        ${column.name} = ${r'#{'}${column.propertyName}${r'}'},
      </if>
	    </#if>
	  </#list> 
    </set>
    where ${pkColumn.name} = ${r'#{'}${pkColumn.propertyName}${r'}'}
    <include refid="IsDelete_Where_Clause" />
  </update>
   
   <insert id="batchInsert" parameterType="java.util.List" useGeneratedKeys="true" keyProperty="${pkColumn.propertyName}">
		insert into ${tableName} (
			${columnList }
		) 
		values
	    <foreach collection="list" item="item" index="index" separator="," >
	       <trim prefix="(" suffix=")" suffixOverrides=",">
	           <#list columns as column>
			    ${r'#{item.'}${column.propertyName}${r'}'},
			   </#list>
	       </trim>
	    </foreach>
	</insert>
	
	
	
	
	
	
	
	
	<!-- 自定义查询扩展     -->
</mapper>