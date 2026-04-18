BACKEND_DIR=backend
FRONTEND_DIR=frontend

.DEFAULT_GOAL := help

.PHONY: help db stop backend frontend dev

help:
	@echo "make db        - start database"
	@echo "make backend   - run spring boot (dev, no tests)"
	@echo "make frontend  - run angular"
	@echo "make dev       - run db + backend + frontend"
	@echo "make stop      - stop database"

# start database from backend folder
db:
	cd $(BACKEND_DIR) && docker compose up -d

# stop database
stop:
	cd $(BACKEND_DIR) && docker compose down

# run spring in dev mode, skip tests completely
backend:
	cd $(BACKEND_DIR) && \
	bash -c "source $$HOME/.sdkman/bin/sdkman-init.sh && sdk env && ./mvnw spring-boot:run -Dmaven.test.skip=true"


# run angular dev server
frontend:
	cd $(FRONTEND_DIR) && ng serve

# full development environment
dev:
	make db
	make -j2 backend frontend
