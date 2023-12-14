<!DOCTYPE html>
<html>
<head>
    <title>Gitee User Search Results</title>
    <style>
        .result {
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 10px;
            display: flex;
            align-items: center;
        }

        .avatar {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            margin-right: 10px;
        }

        .info {
            flex: 1;
        }

        .info h3 {
            margin: 0;
            font-size: 18px;
        }

        .info p {
            margin: 5px 0;
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="results">
    <!-- Render each search result -->
    <#list users as user>
        <div class="result">
            <img class="avatar" src="${user.avatarUrl}" alt="Avatar">
            <div class="info">
                <h3>${user.name}</h3>
                <p>Login: ${user.login}</p>
                <p>
                    Profile:
                    <a href="${user.url}">${user.url}</a>
                </p>
            </div>
        </div>
    </#list>

</div>
</body>
</html>