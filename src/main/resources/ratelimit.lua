if KEYS[1] == nil then
    return -1
end

if(redis.pcall("EXISTS",KEYS[1])==0)
then
--第一次访问，初始化
redis.pcall("HMSET",KEYS[1],
        "last_mill_second",ARGV[1],
        "curr_permits",1,
        "max_burst",10,
        "rate",1,
        "app","skynet")
end

local ratelimit_info=redis.pcall("HMGET",KEYS[1],"last_mill_second","curr_permits","max_burst","rate","app")
local last_mill_second=ratelimit_info[1]
local curr_permits=tonumber(ratelimit_info[2])
local max_burst=tonumber(ratelimit_info[3])
local rate=tonumber(ratelimit_info[4])
local app=tostring(ratelimit_info[5])


local local_curr_permits=max_burst;

if(type(last_mill_second) ~='boolean' and last_mill_second ~=nil) then
	--计算可以加入的最大令牌
    local reverse_permits=math.floor((ARGV[1]-last_mill_second)/1000)*rate
    if(reverse_permits>0) then
		--如果可以加入，则把当前时间作为最后加入令牌时间
        redis.pcall("HMSET",KEYS[1],"last_mill_second",ARGV[1])
    end
	--计算加入令牌后最大值
	--防止节点转发出现时间早的后出现！！！
	reverse_permits=math.max(reverse_permits,0);
    local expect_curr_permits=reverse_permits+curr_permits
	--取最大容量和加入令牌后最大值的最小值作为当前容量
    local_curr_permits=math.min(expect_curr_permits,max_burst);
else
    redis.pcall("HMSET",KEYS[1],"last_mill_second",ARGV[1])
end

local result=-1
if(local_curr_permits-ARGV[2]>0) then
    result=1
	--如果可以获取令牌，则去掉此时需要拿走的令牌
    redis.pcall("HMSET",KEYS[1],"curr_permits",local_curr_permits-ARGV[2])
else
	--如果不行，则把当前最新的令牌数写入内存
    redis.pcall("HMSET",KEYS[1],"curr_permits",local_curr_permits)
end

return result